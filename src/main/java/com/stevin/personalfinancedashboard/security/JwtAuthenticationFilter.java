package com.stevin.personalfinancedashboard.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

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

        //Extract Email from JWT Token
        String email = jwtService.extractUsername(jwt);
        System.out.println("Email : " + email);

        //Load User Details from Database using Email
        UserDetails userDetails = customUserDetailsService
                        .loadUserByUsername(email);

        System.out.println(
                userDetails.getUsername());
        System.out.println("JWT Token : " + jwt);

        if(jwtService.isTokenValid(
                jwt,
                userDetails.getUsername())){

            //Creating Authentication Object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
            //Attaching Request Details to the Authentication Object
            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request));

            //Storing Authentication Object in Security Context
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            System.out.println("Authentication Stored Successfully");

        }

        // Continue request. Without this, the request will not proceed to the controller
        filterChain.doFilter(request, response);

    }
}