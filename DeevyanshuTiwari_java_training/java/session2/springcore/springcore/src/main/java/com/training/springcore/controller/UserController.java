package com.training.springcore.controller;

import com.training.springcore.model.User;
import com.training.springcore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController          // tells Spring: this class handles HTTP requests
@RequestMapping("/users") // base URL for all methods in this class
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /users → returns all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /users/1 → returns user with id 1
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.status(404).body("User not found");
        return ResponseEntity.ok(user);
    }

    // POST /users → creates a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}