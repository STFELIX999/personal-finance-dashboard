package com.stevin.personalfinancedashboard.service.impl;

import com.stevin.personalfinancedashboard.dto.UserRequest;
import com.stevin.personalfinancedashboard.dto.UserResponse;
import org.springframework.stereotype.Service;

import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.repository.UserRepository;
import com.stevin.personalfinancedashboard.service.UserService;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

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
    public User registerUser(UserRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

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

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserResponse> getAllUserResponses() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()))
                .collect(Collectors.toList());
    }
}