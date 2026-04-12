package com.training.springcore.service;

import com.training.springcore.model.User;
import com.training.springcore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service   // tells Spring: "this class has business logic"
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection — Spring automatically gives UserRepository here
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}