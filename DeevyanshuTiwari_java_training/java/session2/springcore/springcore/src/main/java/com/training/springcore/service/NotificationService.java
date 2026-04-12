package com.training.springcore.service;

import com.training.springcore.component.NotificationComponent;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationComponent notificationComponent;

    // Constructor Injection
    public NotificationService(NotificationComponent notificationComponent) {
        this.notificationComponent = notificationComponent;
    }

    public String sendNotification(String recipient) {
        return notificationComponent.generateMessage(recipient);
    }
}