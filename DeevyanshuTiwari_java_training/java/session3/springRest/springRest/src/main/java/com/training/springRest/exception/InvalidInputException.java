package com.training.springRest.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String field, String message) {
        super("Invalid input for '" + field + "': " + message);
    }
}