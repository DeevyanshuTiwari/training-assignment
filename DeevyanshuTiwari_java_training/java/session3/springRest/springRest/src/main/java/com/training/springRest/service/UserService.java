package com.training.springRest.service;

import com.training.springRest.exception.InvalidInputException;
import com.training.springRest.exception.UserNotFoundException;
import com.training.springRest.model.User;
import com.training.springRest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ─── TASK 1: Search/Filter users ───────────────────────────────────
    public List<User> searchUsers(String name, Integer age, String role) {

        // Start with all users
        List<User> result = userRepository.findAll();

        // Filter by name if provided (case-insensitive)
        if (name != null && !name.isEmpty()) {
            result = result.stream()
                    .filter(u -> u.getName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());
        }

        // Filter by age if provided (exact match)
        if (age != null) {
            if (age <= 0)
                throw new InvalidInputException("age", "Age must be a positive number");

            result = result.stream()
                    .filter(u -> u.getAge() == age)
                    .collect(Collectors.toList());
        }

        // Filter by role if provided (case-insensitive)
        if (role != null && !role.isEmpty()) {
            result = result.stream()
                    .filter(u -> u.getRole().equalsIgnoreCase(role))
                    .collect(Collectors.toList());
        }

        return result;
    }

    // ─── TASK 3: Delete with confirmation ──────────────────────────────
    public String deleteUser(int id, Boolean confirm) {

        // Check confirmation flag
        if (confirm == null || !confirm)
            return "Confirmation required";

        // Check if user exists
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Delete the user
        userRepository.deleteById(id);
        return "User with id " + id + " deleted successfully";
    }
}