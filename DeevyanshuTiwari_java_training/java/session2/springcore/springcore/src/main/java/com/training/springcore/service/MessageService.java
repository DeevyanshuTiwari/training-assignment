package com.training.springcore.service;

import com.training.springcore.component.LongMessageFormatter;
import com.training.springcore.component.ShortMessageFormatter;
import com.training.springcore.exception.InvalidInputException;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final ShortMessageFormatter shortFormatter;
    private final LongMessageFormatter longFormatter;

    public MessageService(ShortMessageFormatter shortFormatter,
                          LongMessageFormatter longFormatter) {
        this.shortFormatter = shortFormatter;
        this.longFormatter = longFormatter;
    }

    public String getFormattedMessage(String type, String topic) {
        // Validate type
        if (type == null || type.isEmpty())
            throw new InvalidInputException("type", "Type cannot be empty");

        // Validate type value
        if (!type.equalsIgnoreCase("SHORT") && !type.equalsIgnoreCase("LONG"))
            throw new InvalidInputException("type", "Type must be SHORT or LONG");

        // Validate topic
        if (topic == null || topic.isEmpty())
            throw new InvalidInputException("topic", "Topic cannot be empty");

        if (type.equalsIgnoreCase("SHORT"))
            return shortFormatter.format(topic);
        else
            return longFormatter.format(topic);
    }
}