package com.olgax.hr_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_balances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long employeeId;
    
    @ManyToOne
    @JoinColumn(name = "leave_type_id")
    private LeaveType leaveType;
    
    private int year;
    private int entitled;
    private int used;
}