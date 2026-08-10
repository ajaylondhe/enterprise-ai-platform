package com.enterpriseai.leave.repository;

import com.enterpriseai.leave.entity.Leave;
import com.enterpriseai.leave.entity.LeaveStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository
        extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeEmail(
            String employeeEmail
    );

    List<Leave> findByEmployeeId(
            Long employeeId
    );

    List<Leave> findByStatus(
            LeaveStatus status
    );

    boolean existsByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long employeeId,
            java.time.LocalDate endDate,
            java.time.LocalDate startDate
    );
}