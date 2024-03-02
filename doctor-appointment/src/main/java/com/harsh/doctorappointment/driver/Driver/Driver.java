package com.harsh.doctorappointment.driver.Driver;

import com.harsh.doctorappointment.entity.Appointment;
import com.harsh.doctorappointment.entity.Speciality;
import com.harsh.doctorappointment.entity.TimeSlot;
import com.harsh.doctorappointment.service.AppointmentService;

import java.util.Arrays;
import java.util.Comparator;
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
        appointmentService.registerDoctor("Curious", Speciality.CARDIOLOGIST);
//
        List<TimeSlot> harshAvailability = List.of(
                new TimeSlot(12.5, 13.5)
        );
        appointmentService.markDoctorAvailability("Curious", harshAvailability);
//
//
//        harshAvailability = Arrays.asList(
//                new TimeSlot(9.5, 10.0),
//                new TimeSlot(12.5, 13.0),
//                new TimeSlot(16.0, 16.5)
//        );
//        appointmentService.markDoctorAvailability("Curious", harshAvailability);
//
//        System.out.println();
//
//        // Show Available Slots by Speciality
//        System.out.println("Available Cardiologist Slots:");
//        appointmentService.getAvailableSlotsBySpeciality(Speciality.CARDIOLOGIST)
//                .forEach(doctor -> {
//                    List<TimeSlot> timeSlots = doctor.getAvailability();
//                    timeSlots.sort(Comparator.comparingDouble(TimeSlot::getStartTime));
//                    timeSlots.forEach(slot1 -> System.out.println("Dr. " + doctor.getName() + ":( " + slot1.getStartTime() + "-" + slot1.getEndTime() + " )"));
//
//                });
//
//        System.out.println();
//
//
//        appointmentService.registerDoctor("Dreadful", Speciality.DERMATOLOGIST);
//        List<TimeSlot> soumenAvailability = Arrays.asList(
//                new TimeSlot(9.5, 10.0),
//                new TimeSlot(12.5, 13.0),
//                new TimeSlot(16.0, 16.5)
//        );
//        appointmentService.markDoctorAvailability("Dreadful", soumenAvailability);
//
//
//        // Show Available Slots by Speciality
//        System.out.println("Available DERMATOLOGIST Slots:");
//        appointmentService.getAvailableSlotsBySpeciality(Speciality.DERMATOLOGIST)
//                .forEach(doctor -> {
//                    List<TimeSlot> timeSlots = doctor.getAvailability();
//                    timeSlots.sort(Comparator.comparingDouble(TimeSlot::getStartTime));
//                    timeSlots.forEach(slot1 -> System.out.println("Dr." + doctor.getName() + ":( " + slot1.getStartTime() + "-" + slot1.getEndTime() + " )"));
//
//                });
//
//
//        TimeSlot slot = new TimeSlot(12.5, 13.0);
//
//        System.out.println();
//        // Book Appointment for Patient A for above time slot
//        int bookingResult = appointmentService.bookAppointment(1234, "PatientA", "Curious", slot);
//        System.out.println("Booked, Booking id -> " + bookingResult);
//
//        System.out.println();
//        System.out.println("Available Cardiologist Slots:");
//        appointmentService.getAvailableSlotsBySpeciality(Speciality.CARDIOLOGIST)
//                .forEach(doctor -> {
//                    List<TimeSlot> timeSlots = doctor.getAvailability();
//                    timeSlots.sort(Comparator.comparingDouble(TimeSlot::getStartTime));
//                    timeSlots.forEach(slot1 -> System.out.println(doctor.getName() + ":( " + slot1.getStartTime() + "-" + slot1.getEndTime() + " )"));
//
//                });
//
//        System.out.println();
//
//        System.out.println("Patient A Appointments");
//
//        List<Appointment> appointments = appointmentService.seeAppointments("PatientA", false);
//
//        appointments.forEach(appointment -> {
//            System.out.print(appointment.getDoctor().getName() + ":");
//            System.out.print("( " + appointment.getTimeSlot().getStartTime());
//            System.out.print("-->");
//            System.out.println(appointment.getTimeSlot().getEndTime() + " )");
//        });
//
//        System.out.println();
//        System.out.println("Doctor Curious Appointments");
//        appointments = appointmentService.seeAppointments("Curious", true);
//        appointments.forEach(appointment -> {
//            System.out.print(appointment.getPatient().getName() + ":");
//            System.out.print("( " + appointment.getTimeSlot().getStartTime());
//            System.out.print("-->");
//            System.out.println(appointment.getTimeSlot().getEndTime() + " )");
//        });
//
//
//        // Booking the slot for same patient for same slot
////        int result = appointmentService.bookAppointment(2345,"PatientA", "Soumen", slot);
////        System.out.println(result);
//
//
////        Cancel Appointment
//        appointmentService.cancelBooking(1234);
//
//        System.out.println();
//        System.out.println("Available Cardiologist Slots: ");
//        appointmentService.getAvailableSlotsBySpeciality(Speciality.CARDIOLOGIST)
//                .forEach(doctor -> {
//                    List<TimeSlot> timeSlots = doctor.getAvailability();
//                    timeSlots.sort(Comparator.comparingDouble(TimeSlot::getStartTime));
//                    timeSlots.forEach(slot1 -> System.out.println(doctor.getName() + ":( " + slot1.getStartTime() + "-" + slot1.getEndTime() + " )"));
//
//                });
//
//
//        bookingResult = appointmentService.bookAppointment(5678, "PatientB", "Curious", slot);
//        System.out.println("Booked, Booking id -> " + bookingResult);
//
//
//        System.out.println();
//        System.out.println("Available Cardiologist Slots: ");
//        appointmentService.getAvailableSlotsBySpeciality(Speciality.CARDIOLOGIST)
//                .forEach(doctor -> {
//                    List<TimeSlot> timeSlots = doctor.getAvailability();
//                    timeSlots.sort(Comparator.comparingDouble(TimeSlot::getStartTime));
//                    timeSlots.forEach(slot1 -> System.out.println(doctor.getName() + ":( " + slot1.getStartTime() + "-" + slot1.getEndTime() + " )"));
//
//                });
//
//
//        appointmentService.registerDoctor("Daring", Speciality.DERMATOLOGIST);
//
//        List<TimeSlot> daringAvailability = List.of(
//                new TimeSlot(17.5, 18.0)
//        );
//        appointmentService.markDoctorAvailability("Daring", daringAvailability);
//
//
//    }


    }
}
