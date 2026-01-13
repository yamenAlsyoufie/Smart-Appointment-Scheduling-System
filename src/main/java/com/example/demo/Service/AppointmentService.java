package com.example.demo.Service;

import com.example.demo.Entity.Appointment;
import com.example.demo.Entity.ServiceEntity;
import com.example.demo.Entity.User;
import com.example.demo.Entity.WorkingSchedule;
import com.example.demo.Repository.AppointmentRepository;
import com.example.demo.Repository.ServiceRepository;
import com.example.demo.Repository.WorkingScheduleRepository;
import com.example.demo.exception.AppointmentConflictException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final WorkingScheduleRepository workingScheduleRepository;
    private final ServiceRepository serviceRepository;
    private final EmailService emailService;
    private final GeminiAIService geminiAIService;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            WorkingScheduleRepository workingScheduleRepository,
            ServiceRepository serviceRepository,
            EmailService emailService,
            GeminiAIService geminiAIService
    ) {
        this.appointmentRepository = appointmentRepository;
        this.workingScheduleRepository = workingScheduleRepository;
        this.serviceRepository = serviceRepository;
        this.emailService = emailService;
        this.geminiAIService = geminiAIService;
    }


    public Appointment createAppointment(
            User user,
            Long serviceId,
            LocalDate date,
            LocalTime startTime
    ) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppointmentConflictException("Service not found"));

        LocalTime endTime = startTime.plusMinutes(service.getDuration());

        WorkingSchedule schedule = workingScheduleRepository
                .findByDayOfWeek(date.getDayOfWeek())
                .orElseThrow(() -> new AppointmentConflictException("No schedule"));

        if (schedule.isHoliday()) {
            throw new AppointmentConflictException("هذا اليوم عطلة");
        }

        if (startTime.isBefore(schedule.getStartTime()) || endTime.isAfter(schedule.getEndTime())) {
            throw new AppointmentConflictException("خارج أوقات الدوام");
        }
        Long providerId = service.getProvider().getId();

        List<Appointment.AppointmentStatus> blockingStatuses =
                List.of(
                        Appointment.AppointmentStatus.PENDING,
                        Appointment.AppointmentStatus.APPROVED
                );
//الاساسي
        boolean hasConflict = appointmentRepository.existsOverlappingAppointment(
                providerId,
                date,
                startTime,
                endTime,
                blockingStatuses
        );

        if (hasConflict) {
            throw new AppointmentConflictException("لا يمكن الحجز، الوقت محجوز مسبقًا");
        }
//        // الاحتياط
//        boolean conflict = appointmentRepository.existsConflict(
//                date,
//                startTime,
//                endTime,
//                service.getProvider().getId()
//        );
//
//        if (conflict) {
//            throw new AppointmentConflictException("الموعد متداخل");
//        }




        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setService(service);
        appointment.setDate(date);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }

    public Appointment changeStatus(Long appointmentId, Appointment.AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentConflictException("الموعد غير موجود"));

        appointment.setStatus(status);
        appointmentRepository.save(appointment);

        // ✅ Send email with appointment details
        if (appointment.getUser() != null && appointment.getUser().getEmail() != null) {
            emailService.sendAppointmentStatusEmail(appointment);
        } else {
            System.out.println("⚠ Cannot send email: user or email is null");
        }

        return appointment;
    }



    public String suggestBestAppointment(
            LocalDate date,
            Long serviceId
    ) {

        // 1️⃣ Get service
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppointmentConflictException("Service not found"));

        // 2️⃣ Working schedule
        WorkingSchedule schedule = workingScheduleRepository
                .findByDayOfWeek(date.getDayOfWeek())
                .orElseThrow(() -> new AppointmentConflictException("No working schedule"));

        if (schedule.isHoliday()) {
            throw new AppointmentConflictException("هذا اليوم عطلة");
        }

        // 3️⃣ Booked appointments
        List<Appointment> appointments =
                appointmentRepository.findByDate(date);

        // 4️⃣ Build prompt
        StringBuilder prompt = new StringBuilder();

        prompt.append("Working hours:\n")
                .append(schedule.getStartTime())
                .append(" - ")
                .append(schedule.getEndTime())
                .append("\n\n");

        prompt.append("Booked appointments:\n");

        if (appointments.isEmpty()) {
            prompt.append("None\n");
        } else {
            for (Appointment a : appointments) {
                prompt.append(a.getStartTime())
                        .append(" - ")
                        .append(a.getEndTime())
                        .append("\n");
            }
        }

        prompt.append("\nService:\n")
                .append(service.getName())
                .append(", duration ")
                .append(service.getDuration())
                .append(" minutes\n\n");

        prompt.append("""
        Tasks:
        1. Suggest the best available time slot.
        2. Suggest an alternative if the requested time is unavailable.
        Respond ONLY in JSON format like:
        {
          "bestSlot": "HH:mm-HH:mm",
          "alternativeSlot": "HH:mm-HH:mm"
        }
        """);

        return geminiAIService.askGemini(prompt.toString());
    }
}
