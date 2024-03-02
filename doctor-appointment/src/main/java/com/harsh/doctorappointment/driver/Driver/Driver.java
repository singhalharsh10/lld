package com.harsh.doctorappointment.driver.Driver;

import com.harsh.doctorappointment.entity.Speciality;
import com.harsh.doctorappointment.entity.TimeSlot;
import com.harsh.doctorappointment.service.AppointmentService;

import java.util.Arrays;
import java.util.List;

public class Driver {

    public static void main(String[] args) {
        AppointmentService appointmentService = new AppointmentService();

        // Register Doctors
        appointmentService.registerDoctor("Curious", Speciality.CARDIOLOGIST);
        appointmentService.registerDoctor("Dreadful", Speciality.DERMATOLOGIST);
        appointmentService.registerDoctor("Daring", Speciality.DERMATOLOGIST);

        // Mark Doctor Availability
        List<TimeSlot> curiousAvailability = Arrays.asList(
                new TimeSlot(9, 9.5),
                new TimeSlot(12.5, 13),
                new TimeSlot(16, 16.5)
        );
        appointmentService.markDoctorAvailability("Curious", curiousAvailability);

        List<TimeSlot> dreadfulAvailability = Arrays.asList(
                new TimeSlot(9.5, 10),
                new TimeSlot(12.5, 13),
                new TimeSlot(16, 16.5)
        );
        appointmentService.markDoctorAvailability("Dreadful", dreadfulAvailability);

        List<TimeSlot> daringAvailability = Arrays.asList(
                new TimeSlot(11.5, 12),
                new TimeSlot(14, 14.5)
        );
        appointmentService.markDoctorAvailability("Daring", daringAvailability);

        // Show Available Slots by Speciality
        System.out.println("Available Cardiologist Slots:");
        appointmentService.getAvailableSlotsBySpeciality(Speciality.CARDIOLOGIST)
                .forEach(slot -> System.out.println("Dr.Curious: (" + slot.getStartTime() + "-" + slot.getEndTime() + ")"));

        TimeSlot slot = new TimeSlot(12.5,13);
        // Book Appointment
        int bookingResult = appointmentService.bookAppointment("PatientA", "Curious", slot);
        System.out.println(bookingResult);
        .
        // Cancel Appointment
        appointmentService.cancelBooking( 1234);



    }
}
