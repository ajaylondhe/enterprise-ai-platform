package com.enterpriseai.leave.dto;

import com.enterpriseai.leave.entity.LeaveStatus;

import java.time.LocalDate;

public class LeaveResponse {

    private Long id;

    private Long employeeId;

    private String employeeEmail;

    private String leaveType;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    private LeaveStatus status;

    private String approvedBy;

    private String rejectionReason;

    public LeaveResponse() {
    }

    public LeaveResponse(
            Long id,
            Long employeeId,
            String employeeEmail,
            String leaveType,
            LocalDate startDate,
            LocalDate endDate,
            String reason,
            LeaveStatus status,
            String approvedBy,
            String rejectionReason) {

        this.id = id;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.approvedBy = approvedBy;
        this.rejectionReason = rejectionReason;
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }
}