package com.training.springcore.component;

import org.springframework.stereotype.Component;

@Component  // general-purpose reusable Spring-managed class
public class NotificationComponent {

    public String generateMessage(String recipient) {
        return "Hello " + recipient + "! Your notification has been sent successfully.";
    }
}