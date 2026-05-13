package com.example.employeemanagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JwtFilter runs on EVERY request before it reaches the Controller
// It checks if the request has a valid JWT token in the header
// If valid → allow the request
// If invalid → reject with 401 Unauthorized
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header from request
        // It looks like: "Bearer eyJhbGciOiJIUzI1NiJ9..."
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        // Check if header exists and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract just the token part (remove "Bearer ")
            token = authHeader.substring(7);
            // Extract email from token
            email = jwtUtil.extractEmail(token);
        }

        // If email found and user not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Validate token
            if (jwtUtil.isTokenValid(token)) {
                // Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in Spring Security context
                // This means: "This user is authenticated!"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue to next filter or controller
        filterChain.doFilter(request, response);
    }
}