package com.example.demo.Controller;

import com.example.demo.Entity.Appointment;
import com.example.demo.Repository.AppointmentRepository;
import com.example.demo.Service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;
    private final AppointmentRepository appointmentRepository;

    public EmailController(EmailService emailService,
                           AppointmentRepository appointmentRepository) {
        this.emailService = emailService;
        this.appointmentRepository = appointmentRepository;
    }

    @PostMapping("/appointment/{id}")
    public ResponseEntity<String> sendEmail(@PathVariable Long id) {

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isEmpty()) {
            return ResponseEntity.badRequest().body("Appointment not found");
        }

        emailService.sendAppointmentStatusEmail(appointment.get());

        return ResponseEntity.ok("Email + WebSocket notification sent");
    }
}
