package com.stevin.personalfinancedashboard.service.impl;

import org.springframework.stereotype.Service;

import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.repository.UserRepository;
import com.stevin.personalfinancedashboard.service.UserService;

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
}