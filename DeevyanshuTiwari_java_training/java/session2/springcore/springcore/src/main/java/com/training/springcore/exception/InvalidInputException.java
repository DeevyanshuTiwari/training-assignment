package com.training.springcore.exception;

public class InvalidInputException extends RuntimeException {

    private String field;

    public InvalidInputException(String field, String message) {
        super("Invalid input for '" + field + "': " + message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}