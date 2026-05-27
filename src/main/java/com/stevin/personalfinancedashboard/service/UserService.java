package com.stevin.personalfinancedashboard.service;

import com.stevin.personalfinancedashboard.dto.UserResponse;
import com.stevin.personalfinancedashboard.entity.User;
import java.util.List;

public interface UserService {

    String getWelcomeMessage();

    User registerUser(User user);

    boolean login(String email, String password);

    List<User> getAllUsers();
    List<UserResponse> getAllUserResponses();
}