package com.nucleusTeq.user_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @GetMapping("/me")
    public Map<String, String> myProfile(Authentication authentication) {
        // This endpoint is protected and can be called only with a valid Bearer token.
        Map<String, String> response = new HashMap<>();
        response.put("message", "Token is valid.");
        response.put("userEmail", authentication.getName());
        return response;
    }
}
