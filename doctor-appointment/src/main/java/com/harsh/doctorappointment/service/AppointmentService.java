package com.harsh.doctorappointment.service;

import com.harsh.doctorappointment.entity.*;

import java.util.*;
import java.util.stream.Collectors;

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

//    public List<TimeSlot> showAvailabilityBySpeciality(String speciality) {
//        List<TimeSlot> availableSlots = new ArrayList<>();
//        for (Doctor doctor : doctors) {
//            if (doctor.getSpeciality().equals(speciality)) {
//                availableSlots.addAll(doctor.getAvailability());
//            }
//        }
//        availableSlots.sort(Comparator.comparingDouble(TimeSlot::getStartTime));
//        return availableSlots;
//    }

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

    public Boolean evaluateOverLapTimeStamp(TimeSlot unavailableTImeSlot, TimeSlot requestedTimeSlot){
        if(unavailableTImeSlot.getStartTime()==requestedTimeSlot.getStartTime())
            return true;
        return false;

    }

    public Boolean checkOverLapTimeSlot(TimeSlot givenTimeSlot, List<Appointment>appointments, Doctor doctor){
        Optional<Appointment> filteredAppointment =appointments.stream().filter(appointment -> evaluateOverLapTimeStamp(appointment.getTimeSlot(),givenTimeSlot)).filter(appointment -> !Objects.equals(appointment.getDoctor().getName(), doctor.getName())).findFirst();
        return filteredAppointment.isPresent();

    }

//    public void addToWaitlist(String patientName, String doctorName, TimeSlot timeSlot){
//        waitlist.get(doctorName).add({patientName,t})
//    }

    public int bookAppointment(Integer bookingId, String patientName, String doctorName, TimeSlot timeSlot) throws Exception {
        Doctor doctor = findDoctorByName(doctorName);
        double slotTime = timeSlot.getEndTime()-timeSlot.getStartTime();

        if(slotTime==.50){
            if (isSlotAvailable(doctor, timeSlot)) {
                Patient patient = findOrCreatePatient(patientName);
                List<Appointment>patientAppointments = patient.getAppointments();
                if(checkOverLapTimeSlot(timeSlot,patientAppointments,doctor)){
                    throw  new Exception("Appointment is Already Present for the Patient "+ patientName +" with Different Doctor within time slot");
                }
                Appointment appointment = new Appointment(bookingId,patient, doctor, timeSlot);
                doctor.getAppointments().add(appointment);
                removeSlotFromDoctorAvailability(doctor,timeSlot);
                patient.getAppointments().add(appointment);
                appointments.put(bookingId, appointment);
                return bookingId;
            } else {
//                addToWaitlist(patientName, doctorName, timeSlot);
                return -1;
            }
        }else{
            throw  new Exception("Slot must be of 30 minutes"+ slotTime );
        }


    }

    private void removeSlotFromDoctorAvailability(Doctor doctor, TimeSlot timeSlot) {
        Optional<TimeSlot> getTimeSlot = doctor.getAvailability().stream().filter(tSlot -> Objects.equals(tSlot.getStartTime(), timeSlot.getStartTime())).findFirst();
        getTimeSlot.ifPresent(slot -> doctor.getAvailability().remove(slot));
    }

    public boolean cancelBooking(int bookingId) {
        if (appointments.containsKey(bookingId)) {
            Appointment appointment = appointments.get(bookingId);
            Doctor doctor = appointment.getDoctor();
            doctor.getAppointments().remove(appointment);
            doctor.getAvailability().add(appointment.getTimeSlot());
            appointments.remove(bookingId);
//            tryToBookFromWaitlist(doctor.getName());
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



//    private void tryToBookFromWaitlist(String doctorName) {
//        List<Patient> waitQueue = waitlist.get(doctorName);
//
//    }


    public  List<Appointment> seeAppointments(String name, Boolean isDoctor){

        if(isDoctor){
            return doctors.stream().filter(doctor -> Objects.equals(doctor.getName(), name)).findFirst().get().getAppointments();
        }else{
            return patients.stream().filter(patient -> Objects.equals(patient.getName(), name)).findFirst().get().getAppointments();
        }

    }
}
