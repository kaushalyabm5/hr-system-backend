package com.olgax.hr_system.service;

import com.olgax.hr_system.entity.AttendanceRecord;
import com.olgax.hr_system.repository.AttendanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    // 1. Check-in 
    public AttendanceRecord checkIn(Long employeeId) {
        AttendanceRecord record = new AttendanceRecord();
        record.setEmployeeId(employeeId);
        record.setWorkDate(LocalDate.now());
        record.setCheckIn(LocalDateTime.now());
        return attendanceRecordRepository.save(record);
    }

    // 2. Check-out 
    public AttendanceRecord checkOut(Long employeeId) {
        // Find the record that checked in for today but has not checked out yet
        AttendanceRecord record = attendanceRecordRepository.findAll()
                .stream()
                .filter(r -> r.getEmployeeId().equals(employeeId) 
                          && r.getWorkDate().equals(LocalDate.now()) 
                          && r.getCheckOut() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Active check-in not found for today!"));

        record.setCheckOut(LocalDateTime.now());
        return attendanceRecordRepository.save(record);
    }
}