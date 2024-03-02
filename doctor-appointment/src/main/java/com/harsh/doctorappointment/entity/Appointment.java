package com.harsh.doctorappointment.entity;

public class Appointment {
    private Integer id;
    private Patient patient;
    private Doctor doctor;
    private TimeSlot timeSlot;

    public Appointment(Patient patient, Doctor doctor, TimeSlot timeSlot) {
        this.patient = patient;
        this.doctor = doctor;
        this.timeSlot = timeSlot;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

}
