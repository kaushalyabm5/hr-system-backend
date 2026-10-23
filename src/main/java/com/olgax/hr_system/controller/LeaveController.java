package com.olgax.hr_system.controller;

import com.olgax.hr_system.entity.LeaveRequest;
import com.olgax.hr_system.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // 1. send a new leave request (POST Endpoint)
    // URL: http://localhost:8080/api/leaves/apply
    @PostMapping("/apply")
    public LeaveRequest applyLeave(@RequestBody LeaveRequest leaveRequest) {
        return leaveService.applyLeave(leaveRequest);
    }

    // 2. get all leave requests (GET Endpoint)
    // URL: http://localhost:8080/api/leaves
    @GetMapping
    public List<LeaveRequest> getAllLeaves() {
        return leaveService.getAllLeaveRequests();
    }

    // 3. approve a leave request (PUT Endpoint)
    // URL: http://localhost:8080/api/leaves/1/approve?decidedBy=10
    @PutMapping("/{id}/approve")
    public LeaveRequest approveLeave(@PathVariable Long id, @RequestParam Long decidedBy) {
        return leaveService.approveLeave(id, decidedBy);
    }

    // 4. reject a leave request (PUT Endpoint)
    // URL: http://localhost:8080/api/leaves/1/reject?decidedBy=10
    @PutMapping("/{id}/reject")
    public LeaveRequest rejectLeave(@PathVariable Long id, @RequestParam Long decidedBy) {
        return leaveService.rejectLeave(id, decidedBy);
    }

    // 5. PENDING request delete (DELETE Endpoint)
    // URL: http://localhost:8080/api/leaves/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeave(@PathVariable Long id) {
        String message = leaveService.deleteLeave(id);
        return ResponseEntity.ok(message);
    }
}