package com.harsh.doctorappointment.driver.Driver;

import com.harsh.doctorappointment.entity.Appointment;
import com.harsh.doctorappointment.entity.Speciality;
import com.harsh.doctorappointment.entity.TimeSlot;
import com.harsh.doctorappointment.service.AppointmentService;

import java.util.Arrays;
import java.util.List;

public class Driver {

//    public static TimeSlot convertToTimeSlot(String timeSlot) throws Exception {
//        String[] times = timeSlot.split("-");
//        if (times.length != 2) {
//            //throw Exception;
//            throw  new Exception("Invalid Time Structure Input");
//        }
//        String startTime = times[0];
//        String endTime = times[1];
//
//
//        return new TimeSlot(startTime,endTime);
//
//    }


    public static void main(String[] args) throws Exception {
        AppointmentService appointmentService = new AppointmentService();

        // Register Doctors
        appointmentService.registerDoctor("Harsh", Speciality.CARDIOLOGIST);
        appointmentService.registerDoctor("Soumen", Speciality.DERMATOLOGIST);

        List<TimeSlot> harshAvailability = Arrays.asList(
                new TimeSlot(9.5, 10.0),
                new TimeSlot(12.5, 13.0),
                new TimeSlot(16.0, 16.5)
        );


        appointmentService.markDoctorAvailability("Harsh", harshAvailability);

        List<TimeSlot> soumenAvailability = Arrays.asList(
                new TimeSlot(9.5, 10.0),
                new TimeSlot(12.5, 13.0),
                new TimeSlot(16.0, 16.5)
        );
        appointmentService.markDoctorAvailability("Soumen", soumenAvailability);


        // Show Available Slots by Speciality
        System.out.println("Available Cardiologist Slots:");
        appointmentService.getAvailableSlotsBySpeciality(Speciality.CARDIOLOGIST)
                .forEach(slot -> System.out.println("Dr.Harsh: (" + slot.getStartTime() + "-" + slot.getEndTime() + ")"));

        TimeSlot slot = new TimeSlot(12.5,13.0);
        // Book Appointment
        int bookingResult = appointmentService.bookAppointment(1234,"PatientA", "Harsh", slot);
        System.out.println(bookingResult);

//
//        int result = appointmentService.bookAppointment(2345,"PatientA", "Soumen", slot);
//        System.out.println(result);



        System.out.println("Available Cardiologist Slots:");
        appointmentService.getAvailableSlotsBySpeciality(Speciality.CARDIOLOGIST)
                .forEach(slot1 -> System.out.println("Dr.Harsh: (" + slot1.getStartTime() + "-" + slot1.getEndTime() + ")"));

//        Cancel Appointment
//        appointmentService.cancelBooking(1234 );


        System.out.println("Available Cardiologist Slots:");
        appointmentService.getAvailableSlotsBySpeciality(Speciality.CARDIOLOGIST)
                .forEach(slot1 -> System.out.println("Dr.Harsh: (" + slot1.getStartTime() + "-" + slot1.getEndTime() + ")"));



        List<Appointment> appointments = appointmentService.seeAppointments("Harsh", true);
        appointments.forEach(appointment -> {
            System.out.println(appointment.getTimeSlot().getStartTime());
            System.out.println(appointment.getTimeSlot().getEndTime());
        });

    }
}
