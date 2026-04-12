package com.training.springcore.controller;

import com.training.springcore.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // GET /notification/send?recipient=Rahul
    @GetMapping("/send")
    public String sendNotification(@RequestParam String recipient) {
        return notificationService.sendNotification(recipient);
    }
}