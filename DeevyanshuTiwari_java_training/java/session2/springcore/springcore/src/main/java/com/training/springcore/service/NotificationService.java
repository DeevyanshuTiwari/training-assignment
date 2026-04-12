package com.training.springcore.service;

import com.training.springcore.component.NotificationComponent;
import com.training.springcore.exception.InvalidInputException;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationComponent notificationComponent;

    public NotificationService(NotificationComponent notificationComponent) {
        this.notificationComponent = notificationComponent;
    }

    public String sendNotification(String recipient) {
        // Validate recipient
        if (recipient == null || recipient.isEmpty())
            throw new InvalidInputException("recipient", "Recipient name cannot be empty");

        return notificationComponent.generateMessage(recipient);
    }
}