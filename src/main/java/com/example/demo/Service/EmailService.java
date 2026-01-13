package com.example.demo.Service;

import com.example.demo.Entity.Appointment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final NotificationService notificationService;

    public EmailService(JavaMailSender mailSender,
                        NotificationService notificationService) {
        this.mailSender = mailSender;
        this.notificationService = notificationService;
    }

    public void sendAppointmentStatusEmail(Appointment appointment) {

        if (appointment == null || appointment.getUser() == null) return;

        String to = appointment.getUser().getEmail();

        String status = appointment.getStatus().name();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String date = appointment.getDate().format(dateFormatter);
        String start = appointment.getStartTime().format(timeFormatter);
        String end = appointment.getEndTime().format(timeFormatter);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yamenalsyoufie@gmail.com");
        message.setTo(to);
        message.setSubject("Appointment Status Update");
        message.setText(
                "Your appointment has been " + status +
                        "\nDate: " + date +
                        "\nTime: " + start + " - " + end
        );

        // 📧 Send Email
        mailSender.send(message);

        // 🔔 Send WebSocket Notification
        notificationService.sendEmailNotification(
                to,
                "Appointment " + status
        );
    }
}
