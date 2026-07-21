package com.stevin.personalfinancedashboard.service;

import com.stevin.personalfinancedashboard.dto.LoginRequest;
import com.stevin.personalfinancedashboard.dto.UserRequest;
import com.stevin.personalfinancedashboard.dto.UserResponse;
import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.exception.DuplicateResourceException;
import com.stevin.personalfinancedashboard.repository.UserRepository;
import com.stevin.personalfinancedashboard.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldRegisterUserSuccessfully() {
        UserRequest request = new UserRequest();

        request.setName("Aadhil Viju");
        request.setEmail("aadhil.viju@gmail.com");
        request.setPassword("user333");

        when(passwordEncoder.encode("user333"))
                .thenReturn("encodedPassword");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User response = userService.registerUser(request);

        assertEquals("Aadhil Viju", response.getName());

        assertEquals("aadhil.viju@gmail.com", response.getEmail());
        assertEquals("ROLE_USER", response.getRole());

        verify(userRepository).save(any(User.class));

    }
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UserRequest request = new UserRequest();

        request.setName("Stevin Felix");
        request.setEmail("stevin.felix@gmail.com");
        request.setPassword("stevin123");

        when(userRepository.findByEmail("stevin.felix@gmail.com"))
                .thenReturn(Optional.of(new User()));
        assertThrows(
                DuplicateResourceException.class,
                () -> userService.registerUser(request));

        verify(userRepository, never())
                .save(any(User.class));

    }

    @Test
    void shouldThrowExceptionForInvalidLogin() {
        LoginRequest request = new LoginRequest();

        request.setEmail("aadhil.viju@gmail.com");
        request.setPassword("wrongPassword");

        when(userRepository.findByEmail("aadhil.viju@gmail.com"))
                .thenReturn(Optional.empty());
        assertThrows(
                RuntimeException.class,
                () -> userService.login(request));

    }
    @Test
    void shouldEncodePassword() {

        // Arrange
        UserRequest request = new UserRequest();
        request.setName("Aadhil Viju");
        request.setEmail("aadhil.viju@gmail.com");
        request.setPassword("user333");

        when(passwordEncoder.encode("user333"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        userService.registerUser(request);

        // Assert
        verify(passwordEncoder, times(1))
                .encode("user333");
    }
    @Test
    void shouldCallSaveMethodOnce() {

        // Arrange
        UserRequest request = new UserRequest();
        request.setName("Aadhil Viju");
        request.setEmail("aadhil.viju@gmail.com");
        request.setPassword("user333");

        when(passwordEncoder.encode("user333"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        userService.registerUser(request);

        // Assert
        verify(userRepository, times(1))
                .save(any(User.class));
    }
    @Test
    void shouldThrowExceptionWhenRepositoryFails() {

        // Arrange
        UserRequest request = new UserRequest();
        request.setName("Aadhil Viju");
        request.setEmail("aadhil.viju@gmail.com");
        request.setPassword("user333");

        when(passwordEncoder.encode("user333"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.registerUser(request)
        );

        assertEquals("Database Error", exception.getMessage());
    }
}
