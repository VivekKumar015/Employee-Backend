package com.example.employeemanagement.dto;

import lombok.Data;

// DTO = Data Transfer Object
// This holds the data sent from frontend during login
// { "email": "john@company.com", "password": "password123" }
@Data
public class LoginRequest {
    private String email;
    private String password;
}