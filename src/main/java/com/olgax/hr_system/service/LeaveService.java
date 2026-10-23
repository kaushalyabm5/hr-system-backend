package com.olgax.hr_system.service;

import com.olgax.hr_system.entity.LeaveRequest;
import com.olgax.hr_system.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private ApprovalWorkflowService approvalWorkflowService; // Workflow Service added here

    // 1. Apply for a new leave request
    public LeaveRequest applyLeave(LeaveRequest leaveRequest) {
        leaveRequest.setStatus("PENDING");
        return leaveRequestRepository.save(leaveRequest);
    }

    // 2. Get all leave requests
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    // 3. Approve a leave request (via Workflow Service)
    public LeaveRequest approveLeave(Long id, Long decidedBy) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
        
        // Get the new status from the generic workflow service
        String newStatus = approvalWorkflowService.processApproval(leaveRequest.getStatus(), "APPROVE", decidedBy);
        
        leaveRequest.setStatus(newStatus);
        leaveRequest.setDecidedBy(decidedBy);
        return leaveRequestRepository.save(leaveRequest);
    }

    // 4. Reject a leave request (via Workflow Service)
    public LeaveRequest rejectLeave(Long id, Long decidedBy) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
        
        // Get the new status from the generic workflow service
        String newStatus = approvalWorkflowService.processApproval(leaveRequest.getStatus(), "REJECT", decidedBy);
        
        leaveRequest.setStatus(newStatus);
        leaveRequest.setDecidedBy(decidedBy);
        return leaveRequestRepository.save(leaveRequest);
    }

    // 5. Delete a leave request (PENDING status only) - newly added
    public String deleteLeave(Long id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
        
        // Validation: Check if the request is PENDING
        if (!"PENDING".equals(leaveRequest.getStatus())) {
            throw new RuntimeException("Only PENDING leave requests can be deleted!");
        }
        
        leaveRequestRepository.deleteById(id);
        return "Leave request deleted successfully with ID: " + id;
    }
}