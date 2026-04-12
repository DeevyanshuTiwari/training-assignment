package com.training.springcore.component;

import org.springframework.stereotype.Component;

@Component
public class ShortMessageFormatter {

    public String format(String topic) {
        return "Quick update: " + topic;
    }
}