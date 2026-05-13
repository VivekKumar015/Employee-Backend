package com.example.employeemanagement;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.entity.Holiday;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Load employees if empty
        if (employeeRepository.count() == 0) {
            employeeRepository.save(new Employee(null, "John",    "Smith",    "john@company.com",    "Engineering", 85000.0, "Senior Developer",   "9876543210", passwordEncoder.encode("password123"), "SUPER_ADMIN"));
            employeeRepository.save(new Employee(null, "Sarah",   "Johnson",  "sarah@company.com",   "Marketing",   72000.0, "Marketing Manager",  "9876543211", passwordEncoder.encode("password123"), "ADMIN"));
            employeeRepository.save(new Employee(null, "Mike",    "Williams", "mike@company.com",    "Engineering", 92000.0, "Tech Lead",          "9876543212", passwordEncoder.encode("password123"), "EMPLOYEE"));
            employeeRepository.save(new Employee(null, "Emily",   "Brown",    "emily@company.com",   "HR",          65000.0, "HR Specialist",      "9876543213", passwordEncoder.encode("password123"), "EMPLOYEE"));
            employeeRepository.save(new Employee(null, "David",   "Jones",    "david@company.com",   "Finance",     78000.0, "Financial Analyst",  "9876543214", passwordEncoder.encode("password123"), "EMPLOYEE"));
            System.out.println("✅ Employees loaded!");
        }

        // Load holidays if empty
        if (holidayRepository.count() == 0) {
            holidayRepository.save(new Holiday(null, "New Year's Day",    "2024-01-01", "National",  "Celebration of the New Year",           "India"));
            holidayRepository.save(new Holiday(null, "Republic Day",      "2024-01-26", "National",  "India's Republic Day",                  "India"));
            holidayRepository.save(new Holiday(null, "Holi",              "2024-03-25", "Festival",  "Festival of Colors",                    "India"));
            holidayRepository.save(new Holiday(null, "Good Friday",       "2024-03-29", "National",  "Christian holiday",                     "India"));
            holidayRepository.save(new Holiday(null, "Eid ul-Fitr",       "2024-04-10", "National",  "End of Ramadan",                        "India"));
            holidayRepository.save(new Holiday(null, "Independence Day",  "2024-08-15", "National",  "India's Independence Day",              "India"));
            holidayRepository.save(new Holiday(null, "Gandhi Jayanti",    "2024-10-02", "National",  "Birthday of Mahatma Gandhi",            "India"));
            holidayRepository.save(new Holiday(null, "Diwali",            "2024-11-01", "Festival",  "Festival of Lights",                    "India"));
            holidayRepository.save(new Holiday(null, "Christmas",         "2024-12-25", "National",  "Christmas Day",                         "India"));
            holidayRepository.save(new Holiday(null, "Dussehra",          "2024-10-12", "Festival",  "Victory of good over evil",             "India"));
            holidayRepository.save(new Holiday(null, "Summer Picnic",     "2024-06-15", "Optional",  "Company optional holiday",              "India"));
            holidayRepository.save(new Holiday(null, "Team Building Day", "2024-09-20", "Optional",  "Company team building optional holiday", "India"));
            System.out.println("✅ Holidays loaded!");
        }
    }
}