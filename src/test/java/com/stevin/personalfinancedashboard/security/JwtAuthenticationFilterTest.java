package com.stevin.personalfinancedashboard.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should authenticate valid JWT")
    void shouldAuthenticateValidJwt() throws Exception {

        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("Bearer valid-jwt-token");

        when(jwtService.extractUsername("valid-jwt-token"))
                .thenReturn("stevin@gmail.com");

        UserDetails userDetails =
                User.withUsername("stevin@gmail.com")
                        .password("password")
                        .roles("USER")
                        .build();

        when(customUserDetailsService
                .loadUserByUsername("stevin@gmail.com"))
                .thenReturn(userDetails);

        when(jwtService.isTokenValid(
                eq("valid-jwt-token"),
                eq("stevin@gmail.com")))
                .thenReturn(true);

        jwtAuthenticationFilter.doFilter(
                request,
                response,
                filterChain);

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        assertNotNull(authentication);

        assertEquals(
                "stevin@gmail.com",
                authentication.getName());

        verify(filterChain)
                .doFilter(request, response);
    }
    @Test
    @DisplayName("Should skip authentication when Authorization header is missing")
    void shouldSkipAuthenticationWhenHeaderIsMissing() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn(null);

        jwtAuthenticationFilter.doFilter(
                request,
                response,
                filterChain);

        assertNull(SecurityContextHolder
                .getContext()
                .getAuthentication());

        verify(jwtService, never())
                .extractUsername(anyString());

        verify(customUserDetailsService, never())
                .loadUserByUsername(anyString());

        verify(filterChain)
                .doFilter(request, response);
    }
    @Test
    @DisplayName("Should skip authentication for non-Bearer Authorization header")
    void shouldSkipAuthenticationForInvalidHeader() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Basic abc123");

        jwtAuthenticationFilter.doFilter(
                request,
                response,
                filterChain);

        assertNull(SecurityContextHolder
                .getContext()
                .getAuthentication());

        verify(jwtService, never())
                .extractUsername(anyString());

        verify(customUserDetailsService, never())
                .loadUserByUsername(anyString());

        verify(filterChain)
                .doFilter(request, response);
    }
    @Test
    @DisplayName("Should not authenticate when JWT is invalid")
    void shouldNotAuthenticateWhenTokenIsInvalid() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer invalid-token");

        when(jwtService.extractUsername("invalid-token"))
                .thenReturn("stevin@gmail.com");

        UserDetails userDetails = User.withUsername("stevin@gmail.com")
                .password("password")
                .roles("USER")
                .build();

        when(customUserDetailsService
                .loadUserByUsername("stevin@gmail.com"))
                .thenReturn(userDetails);

        when(jwtService.isTokenValid(
                "invalid-token",
                "stevin@gmail.com"))
                .thenReturn(false);

        jwtAuthenticationFilter.doFilter(
                request,
                response,
                filterChain);

        assertNull(SecurityContextHolder
                .getContext()
                .getAuthentication());

        verify(filterChain)
                .doFilter(request, response);
    }
    @Test
    @DisplayName("Should always continue the filter chain")
    void shouldAlwaysContinueFilterChain() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn(null);

        jwtAuthenticationFilter.doFilter(
                request,
                response,
                filterChain);

        verify(filterChain, times(1))
                .doFilter(request, response);
    }
    @Test
    @DisplayName("Should load user details for a valid JWT")
    void shouldLoadUserDetailsForValidJwt() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer valid-jwt-token");

        when(jwtService.extractUsername("valid-jwt-token"))
                .thenReturn("stevin@gmail.com");

        UserDetails userDetails = User.withUsername("stevin@gmail.com")
                .password("password")
                .roles("USER")
                .build();

        when(customUserDetailsService.loadUserByUsername("stevin@gmail.com"))
                .thenReturn(userDetails);

        when(jwtService.isTokenValid(
                "valid-jwt-token",
                "stevin@gmail.com"))
                .thenReturn(true);

        jwtAuthenticationFilter.doFilter(
                request,
                response,
                filterChain);

        verify(customUserDetailsService)
                .loadUserByUsername("stevin@gmail.com");
    }
}
