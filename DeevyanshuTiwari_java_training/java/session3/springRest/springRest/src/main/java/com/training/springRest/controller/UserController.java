package com.training.springRest.controller;

import com.training.springRest.model.User;
import com.training.springRest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ─── TASK 1: GET /users/search ─────────────────────────────────────
    // All 3 params are optional — if none passed, it will returns all users
    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String role) {

        return userService.searchUsers(name, age, role);
    }

    // ─── TASK 3: DELETE /users/{id}?confirm=true ───────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable int id,
            @RequestParam(required = false) Boolean confirm) {

        String message = userService.deleteUser(id, confirm);

        // If confirmation was missing or false
        if (message.equals("Confirmation required"))
            return ResponseEntity.status(400).body(message);

        return ResponseEntity.ok(message);
    }
}