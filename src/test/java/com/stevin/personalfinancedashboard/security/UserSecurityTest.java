package com.stevin.personalfinancedashboard.security;

import com.stevin.personalfinancedashboard.config.SecurityConfig;
import com.stevin.personalfinancedashboard.controller.UserController;
import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockitoBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("ADMIN should access /users")
    @WithMockUser(
            username = "admin@gmail.com",
            roles = "ADMIN"
    )
    void shouldAllowAdminAccess() throws Exception {

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
    /*@Test
    @DisplayName("USER should receive 403")
    @WithMockUser(
            username = "user@gmail.com",
            roles = "USER"
    )
    void shouldReturn403ForUser() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());

        verify(userService, never())
                .getAllUsers();
    }

    @Test
    @DisplayName("Anonymous user should receive 401")
    @WithAnonymousUser
    void shouldReturn401ForAnonymous() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());

        verify(userService, never())
                .getAllUsers();
    }*/
}
