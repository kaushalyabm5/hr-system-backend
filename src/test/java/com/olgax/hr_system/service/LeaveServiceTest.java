package com.olgax.hr_system.service;

import com.olgax.hr_system.entity.LeaveRequest;
import com.olgax.hr_system.repository.LeaveRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaveServiceTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private ApprovalWorkflowService approvalWorkflowService;

    @InjectMocks
    private LeaveService leaveService;

    // 1. නිවාඩු ඉල්ලීමක් සාර්ථකව ඉදිරිපත් කිරීම පරීක්ෂා කිරීම (Test Apply Leave)
    @Test
    public void testApplyLeave() {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(1L);
        leaveRequest.setDays(2);

        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);

        LeaveRequest savedLeave = leaveService.applyLeave(leaveRequest);

        assertNotNull(savedLeave);
        assertEquals("PENDING", savedLeave.getStatus());
        verify(leaveRequestRepository, times(1)).save(any(LeaveRequest.class));
    }

    // 2. නිවාඩු ඉල්ලීමක් සාර්ථකව අනුමත කිරීම පරීක්ෂා කිරීම (Test Approve Leave)
    @Test
    public void testApproveLeave_Success() {
        Long leaveId = 1L;
        Long decidedBy = 10L;

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(leaveId);
        leaveRequest.setStatus("PENDING");

        when(leaveRequestRepository.findById(leaveId)).thenReturn(Optional.of(leaveRequest));
        when(approvalWorkflowService.processApproval("PENDING", "APPROVE", decidedBy)).thenReturn("APPROVED");
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);

        LeaveRequest approvedLeave = leaveService.approveLeave(leaveId, decidedBy);

        assertNotNull(approvedLeave);
        assertEquals("APPROVED", approvedLeave.getStatus());
        assertEquals(decidedBy, approvedLeave.getDecidedBy());
        verify(leaveRequestRepository, times(1)).findById(leaveId);
        verify(leaveRequestRepository, times(1)).save(leaveRequest);
    }
}