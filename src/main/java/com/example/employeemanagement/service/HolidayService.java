package com.example.employeemanagement.service;

import com.example.employeemanagement.entity.Holiday;
import com.example.employeemanagement.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    // GET ALL holidays ordered by date
    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAllByOrderByDateAsc();
    }

    // GET holiday by ID
    public Optional<Holiday> getHolidayById(Long id) {
        return holidayRepository.findById(id);
    }

    // CREATE new holiday
    public Holiday createHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    // UPDATE holiday
    public Holiday updateHoliday(Long id, Holiday updated) {
        Holiday existing = holidayRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Holiday not found with id: " + id));

        existing.setName(updated.getName());
        existing.setDate(updated.getDate());
        existing.setType(updated.getType());
        existing.setDescription(updated.getDescription());
        existing.setLocation(updated.getLocation());

        return holidayRepository.save(existing);
    }

    // DELETE holiday
    public void deleteHoliday(Long id) {
        Holiday holiday = holidayRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Holiday not found with id: " + id));
        holidayRepository.delete(holiday);
    }

    // SEARCH holidays
    public List<Holiday> searchHolidays(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return holidayRepository.findAllByOrderByDateAsc();
        }
        return holidayRepository.searchByKeyword(keyword.trim());
    }

    // GET by type
    public List<Holiday> getByType(String type) {
        return holidayRepository.findByType(type);
    }

    // GET total count
    public long getHolidayCount() {
        return holidayRepository.count();
    }
}