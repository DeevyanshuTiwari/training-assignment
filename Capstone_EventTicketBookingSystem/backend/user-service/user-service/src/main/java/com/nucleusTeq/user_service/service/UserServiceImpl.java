package com.nucleusTeq.user_service.service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nucleusTeq.user_service.dto.AuthResponse;
import com.nucleusTeq.user_service.dto.LoginRequest;
import com.nucleusTeq.user_service.dto.RegisterRequest;
import com.nucleusTeq.user_service.entity.User;
import com.nucleusTeq.user_service.repository.UserRepository;
import com.nucleusTeq.user_service.security.JwtService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        validateRegisterRequest(request);

        String normalizedEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);
        String normalizedPhone = normalizePhone(request.getPhone());

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        if (userRepository.existsByPhone(normalizedPhone)) {
            throw new IllegalArgumentException("Phone number is already registered.");
        }

        User user = new User();
        user.setFullName(normalizeFullName(request.getFullName()));
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setPhone(normalizedPhone);
        user.setRole(normalizeRole(request.getRole()));
        user.setLastActivityAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return new AuthResponse("User registered successfully.", savedUser.getEmail(), savedUser.getPhone(),
                savedUser.getRole(), null, savedUser.getFullName());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        validateLoginRequest(request);

        String normalizedEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);
        Optional<User> userOptional = userRepository.findByEmail(normalizedEmail);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(request.getPassword().trim(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        user.setLastActivityAt(LocalDateTime.now());
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse("Login successful.", user.getEmail(), user.getPhone(), user.getRole(), token, user.getFullName());
    }

    private void validateRegisterRequest(RegisterRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required.");
        }
        if (isBlank(request.getFullName())) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (isBlank(request.getEmail()) || !request.getEmail().trim().matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            throw new IllegalArgumentException("Email must be in valid format and end with @gmail.com.");
        }
        if (isBlank(request.getPassword())
                || !request.getPassword().trim().matches("^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,12}$")) {
            throw new IllegalArgumentException(
                    "Password must be 8 to 12 characters and include at least one uppercase letter and one special character.");
        }
        if (isBlank(request.getPhone())) {
            throw new IllegalArgumentException("Phone number is required.");
        }
        if (isBlank(request.getRole())) {
            throw new IllegalArgumentException("Role is required (CUSTOMER or ORGANIZER).");
        }
    }

    private void validateLoginRequest(LoginRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required.");
        }
        if (isBlank(request.getEmail())) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Password is required.");
        }
    }

    private String normalizeRole(String role) {
        String normalizedRole = role.trim().toUpperCase(Locale.ROOT);
        if (!"CUSTOMER".equals(normalizedRole) && !"ORGANIZER".equals(normalizedRole)) {
            throw new IllegalArgumentException("Role must be CUSTOMER or ORGANIZER.");
        }
        return normalizedRole;
    }

    private String normalizeFullName(String fullName) {
        String normalizedName = fullName.trim();
        if (!normalizedName.matches("^[A-Za-z ]{2,}$")) {
            throw new IllegalArgumentException("Name must be at least 2 characters and contain alphabets only.");
        }
        return normalizedName;
    }

    private String normalizePhone(String phone) {
        String normalizedPhone = phone.trim();
        if (!normalizedPhone.matches("^\\d{10}$")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits and numeric only.");
        }
        return normalizedPhone;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
