package com.stevin.personalfinancedashboard.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stevin.personalfinancedashboard.dto.LoginRequest;
import com.stevin.personalfinancedashboard.dto.LoginResponse;
import com.stevin.personalfinancedashboard.dto.UserRequest;
import com.stevin.personalfinancedashboard.dto.UserResponse;
import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.security.*;
import com.stevin.personalfinancedashboard.service.UserService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithAnonymousUser;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

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
    @DisplayName("Should return exact welcome message")
    void shouldReturnExactWelcomeMessage() throws Exception {

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

    @Test
    @DisplayName("Should login successfully")
    void shouldLoginSuccessfully() throws Exception {

        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("aadhil.viju@gmail.com");
        request.setPassword("user333");

        LoginResponse response =
                new LoginResponse("dummy-jwt-token");

        when(userService.login(any(LoginRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token")
                        .value("dummy-jwt-token"));
    }
    @Test
    @DisplayName("Should return 400 when registration request is invalid")
    void shouldReturnBadRequestForInvalidRegistration() throws Exception {

        UserRequest request = new UserRequest();
        request.setName("");
        request.setEmail("invalid-email");
        request.setPassword("123");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(userService, never())
                .registerUser(any(UserRequest.class));
    }
    @Test
    @DisplayName("Should return safe users")
    void shouldReturnSafeUsers() throws Exception {

        List<UserResponse> users = List.of(
                new UserResponse(
                        1L,
                        "Aadhil Viju",
                        "aadhil.viju@gmail.com")
        );

        when(userService.getAllUserResponses())
                .thenReturn(users);

        mockMvc.perform(get("/users/safe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Aadhil Viju"))
                .andExpect(jsonPath("$[0].email")
                        .value("aadhil.viju@gmail.com"));
        verify(userService)
                .getAllUserResponses();
    }
    @Test
    @DisplayName("Should return paginated users")
    void shouldReturnPagedUsers() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("Aadhil Viju");
        user.setEmail("aadhil.viju@gmail.com");
        user.setRole("ROLE_USER");

        Page<User> page =
                new PageImpl<>(List.of(user));

        when(userService.getUsers(0,5))
                .thenReturn(page);

        mockMvc.perform(get("/users/paged")
                        .param("page","0")
                        .param("size","5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name")
                        .value("Aadhil Viju"))
                .andExpect(jsonPath("$.content[0].email")
                        .value("aadhil.viju@gmail.com"));
        verify(userService)
                .getUsers(0,5);
    }

    @Test
    @DisplayName("ADMIN should access getAllUsers")
    @WithMockUser(
            username = "admin@gmail.com",
            roles = {"ADMIN"}
    )
    void shouldAllowAdminToAccessUsers() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("Aadhil Viju");
        user.setEmail("aadhil.viju@gmail.com");
        user.setRole("ROLE_USER");

        when(userService.getAllUsers())
                .thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Aadhil Viju"));

        verify(userService)
                .getAllUsers();
    }
    @Test
    @DisplayName("USER should not access getAllUsers")
    @WithMockUser(
            username = "user@gmail.com",
            roles = {"USER"}
    )
    void shouldReturnForbiddenForUserRole() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());

        verify(userService, never())
                .getAllUsers();
    }
    @Test
    @DisplayName("Anonymous user should not access getAllUsers")
    @WithAnonymousUser
    void shouldRejectAnonymousUser() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());

        verify(userService, never())
                .getAllUsers();
    }

}
