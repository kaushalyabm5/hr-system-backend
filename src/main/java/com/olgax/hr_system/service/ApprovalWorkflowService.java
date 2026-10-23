package com.olgax.hr_system.service;

import org.springframework.stereotype.Service;

@Service
public class ApprovalWorkflowService {

    // Generic approval logic
// This can be reused for leave, appraisal, or any request.
    
    public String processApproval(String currentStatus, String action, Long decidedBy) {
        if (!"PENDING".equalsIgnoreCase(currentStatus)) {
            throw new RuntimeException("Only PENDING requests can be processed!");
        }

        if ("APPROVE".equalsIgnoreCase(action)) {
            return "APPROVED";
        } else if ("REJECT".equalsIgnoreCase(action)) {
            return "REJECTED";
        } else {
            throw new RuntimeException("Invalid action! Use APPROVE or REJECT.");
        }
    }
}