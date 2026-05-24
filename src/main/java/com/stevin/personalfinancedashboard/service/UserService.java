package com.stevin.personalfinancedashboard.service;

import com.stevin.personalfinancedashboard.entity.User;

public interface UserService {

    String getWelcomeMessage();

    User registerUser(User user);

    boolean login(String email, String password);
}