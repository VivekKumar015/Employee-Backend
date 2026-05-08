package com.example.employeemanagement;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {
        if (employeeRepository.count() == 0) {
            employeeRepository.save(new Employee(null, "John",    "Smith",    "john@company.com",    "Engineering", 85000.0, "Senior Developer",   "9876543210"));
            employeeRepository.save(new Employee(null, "Sarah",   "Johnson",  "sarah@company.com",   "Marketing",   72000.0, "Marketing Manager",  "9876543211"));
            employeeRepository.save(new Employee(null, "Mike",    "Williams", "mike@company.com",    "Engineering", 92000.0, "Tech Lead",          "9876543212"));
            employeeRepository.save(new Employee(null, "Emily",   "Brown",    "emily@company.com",   "HR",          65000.0, "HR Specialist",      "9876543213"));
            employeeRepository.save(new Employee(null, "David",   "Jones",    "david@company.com",   "Finance",     78000.0, "Financial Analyst",  "9876543214"));
            System.out.println("✅ Sample data loaded!");
        }
    }
}