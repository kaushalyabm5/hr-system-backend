package com.olgax.hr_system.controller;

import com.olgax.hr_system.entity.AttendanceRecord;
import com.olgax.hr_system.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // POST Endpoint for Check-in
    // URL: http://localhost:8080/api/attendance/check-in?employeeId=1
    @PostMapping("/check-in")
    public AttendanceRecord checkIn(@RequestParam Long employeeId) {
        return attendanceService.checkIn(employeeId);
    }

    // PUT Endpoint for Check-out
    // URL: http://localhost:8080/api/attendance/check-out?employeeId=1
    @PutMapping("/check-out")
    public AttendanceRecord checkOut(@RequestParam Long employeeId) {
        return attendanceService.checkOut(employeeId);
    }
}