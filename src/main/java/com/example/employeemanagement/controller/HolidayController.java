package com.example.employeemanagement.controller;

import com.example.employeemanagement.entity.Holiday;
import com.example.employeemanagement.service.HolidayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/holidays")
@CrossOrigin(origins = "*")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    // GET ALL holidays
    // URL: GET /api/holidays
    @GetMapping
    public ResponseEntity<List<Holiday>> getAllHolidays() {
        return ResponseEntity.ok(holidayService.getAllHolidays());
    }

    // GET holiday by ID
    // URL: GET /api/holidays/1
    @GetMapping("/{id}")
    public ResponseEntity<?> getHolidayById(@PathVariable Long id) {
        return holidayService.getHolidayById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // CREATE holiday
    // URL: POST /api/holidays
    // Only SUPER_ADMIN and ADMIN can create
    @PostMapping
    public ResponseEntity<?> createHoliday(@Valid @RequestBody Holiday holiday) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(holidayService.createHoliday(holiday));
        } catch (RuntimeException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

    // UPDATE holiday
    // URL: PUT /api/holidays/1
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHoliday(
            @PathVariable Long id,
            @Valid @RequestBody Holiday holiday) {
        try {
            return ResponseEntity.ok(holidayService.updateHoliday(id, holiday));
        } catch (RuntimeException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
    }

    // DELETE holiday
    // URL: DELETE /api/holidays/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHoliday(@PathVariable Long id) {
        try {
            holidayService.deleteHoliday(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
    }

    // SEARCH holidays
    // URL: GET /api/holidays/search?keyword=christmas
    @GetMapping("/search")
    public ResponseEntity<List<Holiday>> search(
            @RequestParam(defaultValue = "") String keyword) {
        return ResponseEntity.ok(holidayService.searchHolidays(keyword));
    }

    // GET by type
    // URL: GET /api/holidays/type/National
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Holiday>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(holidayService.getByType(type));
    }

    // GET stats
    // URL: GET /api/holidays/stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        List<Holiday> all = holidayService.getAllHolidays();
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalHolidays", all.size());
        stats.put("nationalHolidays", all.stream()
            .filter(h -> "National".equals(h.getType())).count());
        stats.put("festivalHolidays", all.stream()
            .filter(h -> "Festival".equals(h.getType())).count());
        stats.put("optionalHolidays", all.stream()
            .filter(h -> "Optional".equals(h.getType())).count());
        return ResponseEntity.ok(stats);
    }
}