package com.stevin.personalfinancedashboard.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("================================================");
        System.out.println("JWT Filter Executed");
        System.out.println("Request URI : " + request.getRequestURI());

        // Read Authorization Header
        String authHeader = request.getHeader("Authorization");
        // No Authorization Header
        if (authHeader == null) {

            System.out.println("No Authorization Header Found");

            filterChain.doFilter(request, response);
            return;
        }

        // Print complete Authorization Header
        System.out.println("Authorization Header : " + authHeader);

        // Not a Bearer Token
        if (!authHeader.startsWith("Bearer ")) {

            System.out.println("Authorization Header is not a Bearer Token");

            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT Token
        String jwt = authHeader.substring(7);

        System.out.println("JWT Token : " + jwt);

        // Continue request
        filterChain.doFilter(request, response);

    }
}