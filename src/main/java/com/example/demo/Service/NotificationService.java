package com.example.demo.Service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendEmailNotification(String email, String message) {
        System.out.println("🔔 WS NOTIFICATION SENT to " + email + " | " + message);
        messagingTemplate.convertAndSend(
                "/topic/notifications",
                "📧 Email sent to " + email + " | " + message
        );
    }

}
