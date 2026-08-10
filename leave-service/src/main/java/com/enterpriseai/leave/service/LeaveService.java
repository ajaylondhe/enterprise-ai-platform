package com.enterpriseai.leave.service;

import com.enterpriseai.common.exception.ResourceNotFoundException;

import com.enterpriseai.leave.dto.LeaveRequest;
import com.enterpriseai.leave.dto.LeaveResponse;

import com.enterpriseai.leave.entity.Leave;
import com.enterpriseai.leave.entity.LeaveStatus;

import com.enterpriseai.leave.repository.LeaveRepository;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveService(
            LeaveRepository leaveRepository) {

        this.leaveRepository = leaveRepository;
    }

    public LeaveResponse create(
            LeaveRequest request) {

        validateDates(request);

        boolean overlapping =
                leaveRepository
                        .existsByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                                request.getEmployeeId(),
                                request.getEndDate(),
                                request.getStartDate()
                        );

        if (overlapping) {

            throw new IllegalArgumentException(
                    "Employee already has leave during this period"
            );
        }

        Leave leave = new Leave();

        leave.setEmployeeId(
                request.getEmployeeId()
        );

        leave.setEmployeeEmail(
                request.getEmployeeEmail()
        );

        leave.setLeaveType(
                request.getLeaveType()
        );

        leave.setStartDate(
                request.getStartDate()
        );

        leave.setEndDate(
                request.getEndDate()
        );

        leave.setReason(
                request.getReason()
        );

        leave.setStatus(
                LeaveStatus.PENDING
        );

        return toResponse(
                leaveRepository.save(leave)
        );
    }

    @Transactional(readOnly = true)
    public List<LeaveResponse> getAll() {

        return leaveRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LeaveResponse getById(
            Long id) {

        Leave leave =
                leaveRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Leave not found with id: "
                                                + id
                                )
                        );

        return toResponse(leave);
    }

    @Transactional(readOnly = true)
    public List<LeaveResponse> getMyLeaves(
            String email) {

        return leaveRepository
                .findByEmployeeEmail(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public LeaveResponse approve(
            Long id,
            String approvedBy) {

        Leave leave =
                findLeave(id);

        if (leave.getStatus() != LeaveStatus.PENDING) {

            throw new IllegalStateException(
                    "Only pending leave can be approved"
            );
        }

        leave.setStatus(
                LeaveStatus.APPROVED
        );

        leave.setApprovedBy(
                approvedBy
        );

        return toResponse(leave);
    }

    public LeaveResponse reject(
            Long id,
            String rejectionReason) {

        Leave leave =
                findLeave(id);

        if (leave.getStatus() != LeaveStatus.PENDING) {

            throw new IllegalStateException(
                    "Only pending leave can be rejected"
            );
        }

        leave.setStatus(
                LeaveStatus.REJECTED
        );

        leave.setRejectionReason(
                rejectionReason
        );

        return toResponse(leave);
    }

    public void delete(Long id) {

        Leave leave =
                findLeave(id);

        leaveRepository.delete(leave);
    }

    private Leave findLeave(
            Long id) {

        return leaveRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Leave not found with id: "
                                        + id
                        )
                );
    }

    private void validateDates(
            LeaveRequest request) {

        LocalDate start =
                request.getStartDate();

        LocalDate end =
                request.getEndDate();

        if (end.isBefore(start)) {

            throw new IllegalArgumentException(
                    "End date cannot be before start date"
            );
        }
    }

    private LeaveResponse toResponse(
            Leave leave) {

        return new LeaveResponse(
                leave.getId(),
                leave.getEmployeeId(),
                leave.getEmployeeEmail(),
                leave.getLeaveType(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getReason(),
                leave.getStatus(),
                leave.getApprovedBy(),
                leave.getRejectionReason()
        );
    }
}