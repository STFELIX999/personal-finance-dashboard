package com.stevin.personalfinancedashboard.service.impl;

import org.springframework.stereotype.Service;

import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.repository.UserRepository;
import com.stevin.personalfinancedashboard.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getWelcomeMessage() {
        return "Welcome to Personal Finance Dashboard!";
    }

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean login(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            return user.getPassword().equals(password);
        }

        return false;
    }
}