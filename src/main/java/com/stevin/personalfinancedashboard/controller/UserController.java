package com.stevin.personalfinancedashboard.controller;

import com.stevin.personalfinancedashboard.dto.LoginResponse;
import com.stevin.personalfinancedashboard.dto.UserRequest;
import com.stevin.personalfinancedashboard.dto.UserResponse;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return userService.login(request);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/safe")
    public List<UserResponse> getSafeUsers() {
        return userService.getAllUserResponses();
    }

    @GetMapping("/paged")
    public Page<User> getExpensesPaged(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size) {

        return userService.getUsers(page, size);
    }
}