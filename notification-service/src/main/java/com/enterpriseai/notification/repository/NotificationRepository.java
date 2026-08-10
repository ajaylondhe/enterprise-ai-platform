package com.enterpriseai.notification.repository;

import com.enterpriseai.notification.entity.Notification;
import com.enterpriseai.notification.entity.Notification.NotificationStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    Page<Notification> findByEmployeeIdAndActiveTrue(
            Long employeeId,
            Pageable pageable
    );

    Page<Notification> findByEmployeeIdAndStatusAndActiveTrue(
            Long employeeId,
            NotificationStatus status,
            Pageable pageable
    );

    long countByEmployeeIdAndStatusAndActiveTrue(
            Long employeeId,
            NotificationStatus status
    );
}