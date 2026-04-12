package com.training.springcore.exception;

public class UserNotFoundException extends RuntimeException {

    private int userId;

    public UserNotFoundException(int userId) {
        super("User not found with id: " + userId);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}