package com.training.springcore.controller;

import com.training.springcore.service.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // GET /message?type=SHORT&topic=SpringBoot
    @GetMapping
    public String getMessage(@RequestParam String type,
                             @RequestParam String topic) {
        return messageService.getFormattedMessage(type, topic);
    }
}