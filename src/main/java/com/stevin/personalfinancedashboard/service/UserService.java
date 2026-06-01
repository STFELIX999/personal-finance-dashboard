package com.stevin.personalfinancedashboard.service;

import com.stevin.personalfinancedashboard.dto.UserRequest;
import com.stevin.personalfinancedashboard.dto.UserResponse;
import com.stevin.personalfinancedashboard.entity.User;
import java.util.List;

import org.springframework.data.domain.Page;

public interface UserService {

    String getWelcomeMessage();

    User registerUser(UserRequest request);

    boolean login(String email, String password);

    List<User> getAllUsers();
    List<UserResponse> getAllUserResponses();

    Page<User> getUsers(int page, int size);
}