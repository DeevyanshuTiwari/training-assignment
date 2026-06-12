package com.nucleusTeq.user_service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nucleusTeq.user_service.entity.User;
import com.nucleusTeq.user_service.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    public static class UserProfileDto {
        public String name;
        public String email;
        public String phone;
    }

    @GetMapping("/me")
    public ResponseEntity<?> myProfile(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, String> response = new HashMap<>();
            response.put("name", user.getFullName());
            response.put("email", user.getEmail());
            response.put("phone", user.getPhone() != null ? user.getPhone() : "");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateProfile(Authentication authentication, @RequestBody UserProfileDto request) {
        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Validate email change
            if (request.email != null && !user.getEmail().equals(request.email) && userRepository.existsByEmail(request.email)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already in use"));
            }
            // Validate phone change
            if (request.phone != null && !request.phone.equals(user.getPhone()) && userRepository.existsByPhone(request.phone)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Phone number already in use"));
            }

            if (request.name != null) user.setFullName(request.name);
            if (request.email != null) user.setEmail(request.email);
            if (request.phone != null) user.setPhone(request.phone);

            userRepository.save(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Profile updated successfully");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    }
}
