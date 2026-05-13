package com.example.employeemanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @Column(name = "department", nullable = false)
    @NotBlank(message = "Department is required")
    private String department;

    @Column(name = "salary", nullable = false)
    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be positive")
    private Double salary;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "phone")
    private String phone;

    // NEW FIELDS FOR AUTHENTICATION
    // Password stored as BCrypt hash — never plain text!
    @Column(name = "password")
    private String password;

    // Role can be: SUPER_ADMIN, ADMIN, EMPLOYEE
    @Column(name = "role")
    private String role;
}