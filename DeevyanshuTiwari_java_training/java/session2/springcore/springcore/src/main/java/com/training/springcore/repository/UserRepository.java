package com.training.springcore.repository;

import com.training.springcore.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository   // tells Spring: "this class handles data"
public class UserRepository {

    // In-memory list acting as our fake database
    private List<User> users = new ArrayList<>();
    private int nextId = 1;

    public UserRepository() {
        // Pre-load some dummy data
        users.add(new User(nextId++, "Deevyanshu tiwari", "tiwari@gmail.com"));
        users.add(new User(nextId++, "Priya Singh", "priya@gmail.com"));
        users.add(new User(nextId++, "Amit Verma", "amit@gmail.com"));
    }

    // Get all users
    public List<User> findAll() {
        return users;
    }

    // Get user by id
    public User findById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Save new user
    public User save(User user) {
        user.setId(nextId++);
        users.add(user);
        return user;
    }
}