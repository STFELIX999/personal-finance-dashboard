package com.stevin.personalfinancedashboard.controller;

import com.stevin.personalfinancedashboard.dto.UserRequest;
import com.stevin.personalfinancedashboard.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.service.UserService;

import com.stevin.personalfinancedashboard.dto.LoginRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return userService.getWelcomeMessage();
    }

    @PostMapping("/register")
    public User registerUser(
            @Valid
            @RequestBody UserRequest request) {

        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        boolean isValid =
                userService.login(
                        request.getEmail(),
                        request.getPassword());

        if (isValid) {
            return "Login Successful";
        }

        return "Invalid Email or Password";
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/safe")
    public List<UserResponse> getSafeUsers() {
        return userService.getAllUserResponses();
    }
}