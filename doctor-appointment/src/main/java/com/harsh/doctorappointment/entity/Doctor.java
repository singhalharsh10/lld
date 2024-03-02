package com.harsh.doctorappointment.entity;

import java.util.ArrayList;
import java.util.List;



public class Doctor {



    private String name;
    private Speciality speciality;
    private List<TimeSlot> availability;
    private List<Appointment> appointments;

    public Doctor(String name, Speciality speciality) {
        this.name = name;
        this.speciality = speciality;
        this.availability = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAvailability(TimeSlot timeSlot) {
        availability.add(timeSlot);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

}
