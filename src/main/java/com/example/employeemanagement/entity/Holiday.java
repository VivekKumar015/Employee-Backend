package com.example.employeemanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This creates a "holidays" table in MySQL automatically
@Entity
@Table(name = "holidays")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {

    // Primary key — auto incremented
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Holiday name e.g. "Christmas", "Diwali"
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Holiday name is required")
    private String name;

    // Date of holiday e.g. "2024-12-25"
    @Column(name = "date", nullable = false)
    @NotBlank(message = "Date is required")
    private String date;

    // Type of holiday e.g. "National", "Optional", "Festival"
    @Column(name = "type", nullable = false)
    @NotBlank(message = "Type is required")
    private String type;

    // Short description of the holiday
    @Column(name = "description")
    private String description;

    // Which country/location this holiday applies to
    @Column(name = "location")
    private String location;
}