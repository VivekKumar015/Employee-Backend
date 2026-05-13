package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.LoginRequest;
import com.example.employeemanagement.dto.LoginResponse;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    // LOGIN endpoint
    // URL: POST http://localhost:8080/api/auth/login
    // Body: { "email": "john@company.com", "password": "password123" }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Step 1: Authenticate email and password
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );

            // Step 2: Get employee details from database
            Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

            // Step 3: Generate JWT token with email and role
            String token = jwtUtil.generateToken(employee.getEmail(), employee.getRole());

            // Step 4: Return token + role + name to frontend
            return ResponseEntity.ok(new LoginResponse(
                token,
                employee.getRole(),
                employee.getEmail(),
                employee.getFirstName()
            ));

        } catch (BadCredentialsException e) {
            // Wrong email or password
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid email or password!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}