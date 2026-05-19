package com.nucleusTeq.user_service.dto;

public class AuthResponse {

    private String message;
    private String email;
    private String phone;
    private String role;
    private String token;
    private String fullName;

    public AuthResponse() {
    }

    public AuthResponse(String message, String email, String phone, String role) {
        this.message = message;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public AuthResponse(String message, String email, String phone, String role, String token, String fullName) {
        this.message = message;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.token = token;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
