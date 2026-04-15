package com.training.springRest.repository;

import com.training.springRest.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public UserRepository() {
        // 7 dummy users pre-loaded
        users.add(new User(1, "Rahul Sharma", 25, "USER"));
        users.add(new User(2, "Priya Singh", 30, "ADMIN"));
        users.add(new User(3, "Amit Verma", 30, "USER"));
        users.add(new User(4, "Sneha Patel", 28, "USER"));
        users.add(new User(5, "Rohit Kumar", 35, "ADMIN"));
        users.add(new User(6, "Anjali Mehta", 25, "USER"));
        users.add(new User(7, "Priya Sharma", 30, "USER"));
    }

    // Return all users
    public List<User> findAll() {
        return users;
    }

    // Find user by id
    public Optional<User> findById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    // Delete user by id
    public boolean deleteById(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
}