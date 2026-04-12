package com.training.springcore.service;

import com.training.springcore.exception.InvalidInputException;
import com.training.springcore.exception.UserNotFoundException;
import com.training.springcore.model.User;
import com.training.springcore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        // Validate id
        if (id <= 0)
            throw new InvalidInputException("id", "ID must be a positive number");

        User user = userRepository.findById(id);

        // Throw exception if user not found
        if (user == null)
            throw new UserNotFoundException(id);

        return user;
    }

    public User createUser(User user) {
        // Validate name
        if (user.getName() == null || user.getName().isEmpty())
            throw new InvalidInputException("name", "Name cannot be empty");

        // Validate email
        if (user.getEmail() == null || user.getEmail().isEmpty())
            throw new InvalidInputException("email", "Email cannot be empty");

        // Validate email format
        if (!user.getEmail().contains("@"))
            throw new InvalidInputException("email", "Email must contain @");

        return userRepository.save(user);
    }
}