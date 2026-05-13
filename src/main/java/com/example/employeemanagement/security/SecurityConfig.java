package com.example.employeemanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

// @Configuration = This class contains Spring configuration
// @EnableWebSecurity = Enable Spring Security in this app
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    // Define which URLs are public and which need authentication
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF (not needed for REST APIs with JWT)
            .csrf(csrf -> csrf.disable())

            // Configure CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Define URL permissions
            .authorizeHttpRequests(auth -> auth
                // Login endpoint is PUBLIC — anyone can access
                .requestMatchers("/api/auth/login").permitAll()

                // DELETE only SUPER_ADMIN can delete
                .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("SUPER_ADMIN")

                // POST (add) SUPER_ADMIN and ADMIN can add
                .requestMatchers(HttpMethod.POST, "/api/employees").hasAnyRole("SUPER_ADMIN", "ADMIN")

                // PUT (update) SUPER_ADMIN and ADMIN can update
                .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

                // GET (view) all roles can view
                .requestMatchers(HttpMethod.GET, "/api/employees/**").hasAnyRole("SUPER_ADMIN", "ADMIN", "EMPLOYEE")

                // Everything else needs authentication
                .anyRequest().authenticated()
            )

            // Use stateless session — no cookies, only JWT tokens
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Add our JWT filter before Spring's default filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // BCrypt password encoder — encrypts passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager — handles login authentication
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // CORS configuration — allow React frontend to call backend
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "https://incandescent-mochi-c418dd.netlify.app"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}