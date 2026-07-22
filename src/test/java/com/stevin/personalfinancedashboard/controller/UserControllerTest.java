package com.stevin.personalfinancedashboard.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stevin.personalfinancedashboard.dto.LoginRequest;
import com.stevin.personalfinancedashboard.dto.LoginResponse;
import com.stevin.personalfinancedashboard.dto.UserRequest;
import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.security.*;
import com.stevin.personalfinancedashboard.service.UserService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockitoBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Test
    @DisplayName("Should return welcome message")
    void shouldReturnWelcomeMessage() throws Exception {

        when(userService.getWelcomeMessage())
                .thenReturn("Welcome to Personal Finance Dashboard!");

        mockMvc.perform(get("/users/welcome"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Welcome to Personal Finance Dashboard!"));
    }
    @Test
    @DisplayName("Should register user successfully")
    void shouldRegisterUserSuccessfully() throws Exception {

        UserRequest request = new UserRequest();
        request.setName("Aadhil Viju");
        request.setEmail("aadhil.viju@gmail.com");
        request.setPassword("user333");

        User user = new User();
        user.setId(1L);
        user.setName("Aadhil Viju");
        user.setEmail("aadhil.viju@gmail.com");
        user.setRole("ROLE_USER");

        when(userService.registerUser(any(UserRequest.class)))
                .thenReturn(user);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Aadhil Viju"))
                .andExpect(jsonPath("$.email")
                        .value("aadhil.viju@gmail.com"))
                .andExpect(jsonPath("$.role")
                        .value("ROLE_USER"));
    }

}
