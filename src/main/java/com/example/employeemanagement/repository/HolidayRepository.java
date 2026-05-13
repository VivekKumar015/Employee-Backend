package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    // Find holidays by type
    // SELECT * FROM holidays WHERE type = ?
    List<Holiday> findByType(String type);

    // Search holidays by name or description
    @Query("SELECT h FROM Holiday h WHERE " +
           "LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(h.type) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(h.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Holiday> searchByKeyword(@Param("keyword") String keyword);

    // Find holidays ordered by date
    List<Holiday> findAllByOrderByDateAsc();
}