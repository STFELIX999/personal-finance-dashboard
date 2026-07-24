package com.stevin.personalfinancedashboard.exception;

import com.stevin.personalfinancedashboard.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserExceptionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Register should return 400 when name is blank")
    void shouldReturn400WhenNameIsBlank() throws Exception {

        String request = """
            {
                "name": "",
                "email": "john@gmail.com",
                "password": "password123"
            }
            """;

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Name is required"));

        verify(userService, never())
                .registerUser(any());
    }
    @Test
    @DisplayName("Register should return 409 when email already exists")
    void shouldReturn409WhenEmailAlreadyExists() throws Exception {

        String request = """
            {
                "name": "John",
                "email": "john@gmail.com",
                "password": "password123"
            }
            """;

        when(userService.registerUser(any()))
                .thenThrow(new DuplicateResourceException(
                        "Email already exists"));

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message")
                        .value("Email already exists"));

        verify(userService).registerUser(any());
    }


}
