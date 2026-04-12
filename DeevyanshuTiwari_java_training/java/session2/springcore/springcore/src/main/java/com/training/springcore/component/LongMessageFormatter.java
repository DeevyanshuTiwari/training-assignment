package com.training.springcore.component;

import org.springframework.stereotype.Component;
@Component
public class LongMessageFormatter {

    public String format(String topic) {
        return "Dear User, we wanted to provide you with a detailed update " +
                "regarding the following topic: " + topic +
                ". Please review at your earliest convenience. Thank you.";
    }
}