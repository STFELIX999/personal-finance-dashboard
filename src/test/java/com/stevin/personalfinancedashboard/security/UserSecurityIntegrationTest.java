package com.stevin.personalfinancedashboard.security;

import com.stevin.personalfinancedashboard.dto.UserResponse;
import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Anonymous user should receive 401")
    @WithAnonymousUser
    void shouldReturn401ForAnonymousUser() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());

        verify(userService, never())
                .getAllUsers();
    }
    @Test
    void shouldReturn403ForUserRole() throws Exception {

        mockMvc.perform(
                        get("/users")
                                .with(user("user@gmail.com").roles("USER"))
                )
                .andExpect(status().isForbidden());

        verify(userService, never()).getAllUsers();
    }
    @Test
    @DisplayName("ADMIN should receive 200 OK")
    void shouldReturn200ForAdminRole() throws Exception {

        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John");
        user1.setEmail("john@gmail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Alice");
        user2.setEmail("alice@gmail.com");

        List<User> users = List.of(user1, user2);

        when(userService.getAllUsers())
                .thenReturn(users);

        // Act & Assert
        mockMvc.perform(
                        get("/users")
                                .with(user("admin@gmail.com")
                                        .roles("ADMIN"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].email").value("john@gmail.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Alice"))
                .andExpect(jsonPath("$[1].email").value("alice@gmail.com"));

        verify(userService).getAllUsers();
    }

}
