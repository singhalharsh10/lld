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

    public void registerDoctor(String name, Speciality speciality) {
        Doctor doctor = new Doctor(name, speciality );
        doctors.add(doctor);
    }

    public void markDoctorAvailability(String doctorName, int startTime, int endTime) {
        Doctor doctor = findDoctorByName(doctorName);
        TimeSlot timeSlot = new TimeSlot(startTime, endTime);
        doctor.addAvailability(timeSlot);
    }

    public List<TimeSlot> showAvailabilityBySpeciality(String speciality) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getSpeciality().equals(speciality)) {
                availableSlots.addAll(doctor.getAvailability());
            }
        }
        availableSlots.sort(Comparator.comparingInt(TimeSlot::getStartTime));
        return availableSlots;
    }

    public List<TimeSlot> getAvailableSlotsBySpeciality(Speciality speciality) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getSpeciality() == speciality) {
                availableSlots.addAll(doctor.getAvailability());
            }
        }
        availableSlots.sort(Comparator.comparingInt(TimeSlot::getStartTime));
        return availableSlots;
    }

    public int bookAppointment(String patientName, String doctorName, int startTime, int endTime) {
        Doctor doctor = findDoctorByName(doctorName);
        TimeSlot timeSlot = new TimeSlot(startTime, endTime);

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
            markDoctorAvailability(doctorName, nextPatient.getAppointments().get(0).getTimeSlot().getStartTime(),
                    nextPatient.getAppointments().get(0).getTimeSlot().getEndTime());
        }
    }
}
