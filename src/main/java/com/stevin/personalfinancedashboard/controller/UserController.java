package com.stevin.personalfinancedashboard.controller;

import org.springframework.web.bind.annotation.*;

import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.service.UserService;

import com.stevin.personalfinancedashboard.dto.LoginRequest;

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
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
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
}