package com.training.springcore.service;

import com.training.springcore.component.LongMessageFormatter;
import com.training.springcore.component.ShortMessageFormatter;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final ShortMessageFormatter shortFormatter;
    private final LongMessageFormatter longFormatter;

    // Constructor Injection of BOTH formatters
    public MessageService(ShortMessageFormatter shortFormatter,
                          LongMessageFormatter longFormatter) {
        this.shortFormatter = shortFormatter;
        this.longFormatter = longFormatter;
    }

    // Decision logic lives HERE in service — not in controller
    public String getFormattedMessage(String type, String topic) {
        if (type.equalsIgnoreCase("SHORT"))
            return shortFormatter.format(topic);
        else
            return longFormatter.format(topic);
    }
}