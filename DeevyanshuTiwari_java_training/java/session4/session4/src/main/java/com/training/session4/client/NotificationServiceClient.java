package com.training.session4.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceClient {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceClient.class);

    // Simulates sending a notification to an external system
    public void sendNotification(String message) {
        log.info("NotificationServiceClient called");
        log.info("Notification sent: {}", message);
        // In real project: this would call an external API via RestTemplate/WebClient
    }
}