package com.harsh.doctorappointment.service;

import com.harsh.doctorappointment.entity.*;

import java.util.*;

public class AppointmentService {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private Map<Integer, Appointment> appointments;
    private Map<String, Queue<Patient>> waitlist;

    public AppointmentService() {
        this.doctors = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.appointments = new HashMap<>();
        this.waitlist = new HashMap<>();
    }


    public boolean isValidTimeSlot(String timeSlot) {
        String[] times = timeSlot.split("-");
        if (times.length != 2) {
            return false;
        }
        String startTime = times[0];
        String endTime = times[1];

        String[] startParts = startTime.split(":");
        String[] endParts = endTime.split(":");

        if (startParts.length != 2 || endParts.length != 2) {
            return false;
        }

        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);

        if (endHour == startHour && endMinute - startMinute == 30) {
            return true;
        }

        return false;
    }

    public void registerDoctor(String name, Speciality speciality) throws Exception {
        Doctor doctor = new Doctor(name, speciality );
        Optional<Doctor> isDoctorAlreadyPresent = doctors.stream().filter(existingDoctor-> Objects.equals(existingDoctor.getName(), name)).findFirst();
        if(isDoctorAlreadyPresent.isPresent()){
            throw  new Exception("Doctor is ALready Registered with same name");
        }
        doctors.add(doctor);
        System.out.println("Doctor Registered with name"+ name +" and speciality "+ speciality);

    }

    public void markDoctorAvailability(String doctorName,List<TimeSlot>slots) {
        Doctor doctor = findDoctorByName(doctorName);
        slots.forEach(slot->{
            assert doctor != null;
            doctor.addAvailability(slot);
        });

    }

    public List<TimeSlot> showAvailabilityBySpeciality(String speciality) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getSpeciality().equals(speciality)) {
                availableSlots.addAll(doctor.getAvailability());
            }
        }
        availableSlots.sort(Comparator.comparingDouble(TimeSlot::getStartTime));
        return availableSlots;
    }

    public List<TimeSlot> getAvailableSlotsBySpeciality(Speciality speciality) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getSpeciality() == speciality) {
                availableSlots.addAll(doctor.getAvailability());
            }
        }
        availableSlots.sort(Comparator.comparingDouble(TimeSlot::getStartTime));
        return availableSlots;
    }

    public int bookAppointment(String patientName, String doctorName, TimeSlot timeSlot) throws Exception {
        Doctor doctor = findDoctorByName(doctorName);
        double slotTime = timeSlot.getEndTime()-timeSlot.getStartTime();
        System.out.println(timeSlot.getStartTime());
        System.out.println(timeSlot.getEndTime());
        System.out.println(slotTime);
        if(slotTime==.50){
            if (isSlotAvailable(doctor, timeSlot)) {
                Patient patient = findOrCreatePatient(patientName);
                Appointment appointment = new Appointment(patient, doctor, timeSlot);
                doctor.addAppointment(appointment);
                appointments.put(appointment.hashCode(), appointment);
                return appointment.hashCode();
            } else {
                addToWaitlist(patientName, doctorName);
                return -1;
            }
        }else{
            throw  new Exception("Slot must be of 30 minutes"+ slotTime );
        }


    }

    public boolean cancelBooking(int bookingId) {
        if (appointments.containsKey(bookingId)) {
            Appointment appointment = appointments.get(bookingId);
            Doctor doctor = appointment.getDoctor();
            doctor.getAppointments().remove(appointment);
            appointments.remove(bookingId);
            tryToBookFromWaitlist(doctor.getName());
            return true;
        }
        return false;
    }

    public List<Appointment> getPatientAppointments(String patientName) {
        Patient patient = findPatientByName(patientName);
        return patient.getAppointments();
    }

    private Doctor findDoctorByName(String doctorName) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equals(doctorName)) {
                return doctor;
            }
        }
        return null;
    }

    private Patient findPatientByName(String patientName) {
        for (Patient patient : patients) {
            if (patient.getName().equals(patientName)) {
                return patient;
            }
        }
        return null;
    }

    private Patient findOrCreatePatient(String patientName) {
        Patient patient = findPatientByName(patientName);
        if (patient == null) {
            patient = new Patient(patientName);
            patients.add(patient);
        }
        return patient;
    }

    private boolean isSlotAvailable(Doctor doctor, TimeSlot timeSlot) {
        for (Appointment appointment : doctor.getAppointments()) {
            if (isOverlap(timeSlot, appointment.getTimeSlot())) {
                return false;
            }
        }
        return true;
    }

    private boolean isOverlap(TimeSlot timeSlot1, TimeSlot timeSlot2) {
        return timeSlot1.getStartTime() < timeSlot2.getEndTime() &&
                timeSlot1.getEndTime() > timeSlot2.getStartTime();
    }

    private void addToWaitlist(String patientName, String doctorName) {
        Queue<Patient> waitQueue = waitlist.computeIfAbsent(doctorName, k -> new LinkedList<>());
        waitQueue.add(findOrCreatePatient(patientName));
    }

    private void tryToBookFromWaitlist(String doctorName) {
        Queue<Patient> waitQueue = waitlist.get(doctorName);
        if (waitQueue != null && !waitQueue.isEmpty()) {
            Patient nextPatient = waitQueue.poll();
            TimeSlot slot = new  TimeSlot(nextPatient.getAppointments().get(0).getTimeSlot().getStartTime(),
                    nextPatient.getAppointments().get(0).getTimeSlot().getEndTime());
            markDoctorAvailability(doctorName, (List<TimeSlot>) slot);
        }
    }
}
