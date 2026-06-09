package com.stevin.personalfinancedashboard.service.impl;

import com.stevin.personalfinancedashboard.dto.LoginRequest;
import com.stevin.personalfinancedashboard.dto.UserRequest;
import com.stevin.personalfinancedashboard.dto.UserResponse;
import com.stevin.personalfinancedashboard.exception.DuplicateResourceException;
import com.stevin.personalfinancedashboard.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String getWelcomeMessage() {
        return "Welcome to Personal Finance Dashboard!";
    }

    @Override
    public User registerUser(UserRequest request) {

        if (userRepository.findByEmail(
                request.getEmail()).isPresent()) {

            throw new DuplicateResourceException(
                    "Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

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
    public String login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        if (passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return "Login Successful";
        }

        return "Invalid Credentials";
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

    @Override
    public Page<User> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(pageable);
    }
}