package com.example.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// This is what we send BACK to frontend after successful login
// { "token": "eyJhbGc...", "role": "SUPER_ADMIN", "email": "john@company.com" }
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String role;
    private String email;
    private String firstName;
}