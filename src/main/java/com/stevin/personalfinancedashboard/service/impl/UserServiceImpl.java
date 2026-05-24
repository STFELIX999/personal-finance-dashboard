package com.stevin.personalfinancedashboard.service.impl;

import org.springframework.stereotype.Service;

import com.stevin.personalfinancedashboard.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getWelcomeMessage() {
        return "Welcome to Personal Finance Dashboard!";
    }
}