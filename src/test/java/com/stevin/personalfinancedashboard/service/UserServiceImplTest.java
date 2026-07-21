package com.stevin.personalfinancedashboard.service;

import com.stevin.personalfinancedashboard.dto.UserRequest;
import com.stevin.personalfinancedashboard.dto.UserResponse;
import com.stevin.personalfinancedashboard.entity.User;
import com.stevin.personalfinancedashboard.repository.UserRepository;
import com.stevin.personalfinancedashboard.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
