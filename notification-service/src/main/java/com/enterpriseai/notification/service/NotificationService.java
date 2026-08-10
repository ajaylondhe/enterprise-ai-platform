package com.enterpriseai.notification.service;

import com.enterpriseai.common.exception.ResourceNotFoundException;
import com.enterpriseai.notification.dto.NotificationRequest;
import com.enterpriseai.notification.dto.NotificationResponse;
import com.enterpriseai.notification.entity.Notification;
import com.enterpriseai.notification.repository.NotificationRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;


    public NotificationService(
            NotificationRepository notificationRepository) {

        this.notificationRepository =
                notificationRepository;
    }


    @Transactional
    public NotificationResponse create(
            NotificationRequest request) {

        Notification notification =
                new Notification();

        notification.setEmployeeId(
                request.getEmployeeId()
        );

        notification.setTitle(
                request.getTitle()
        );

        notification.setMessage(
                request.getMessage()
        );

        notification.setType(
                request.getType()
        );

        notification.setReferenceId(
                request.getReferenceId()
        );

        notification.setStatus(
                Notification.NotificationStatus.UNREAD
        );

        notification.setActive(true);

        Notification saved =
                notificationRepository.save(
                        notification
                );

        return toResponse(saved);
    }


    @Transactional(readOnly = true)
    public Page<NotificationResponse> getEmployeeNotifications(
            Long employeeId,
            Pageable pageable) {

        return notificationRepository
                .findByEmployeeIdAndActiveTrue(
                        employeeId,
                        pageable
                )
                .map(this::toResponse);
    }


    @Transactional(readOnly = true)
    public Page<NotificationResponse> getByStatus(
            Long employeeId,
            Notification.NotificationStatus status,
            Pageable pageable) {

        return notificationRepository
                .findByEmployeeIdAndStatusAndActiveTrue(
                        employeeId,
                        status,
                        pageable
                )
                .map(this::toResponse);
    }


    @Transactional
    public NotificationResponse markAsRead(
            Long id) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Notification not found with id: "
                                                + id
                                )
                        );

        notification.setStatus(
                Notification.NotificationStatus.READ
        );

        notification.setReadAt(
                LocalDateTime.now()
        );

        return toResponse(
                notificationRepository.save(
                        notification
                )
        );
    }


    @Transactional
    public void delete(Long id) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Notification not found with id: "
                                                + id
                                )
                        );

        notification.setActive(false);

        notificationRepository.save(notification);
    }


    @Transactional(readOnly = true)
    public long countUnread(Long employeeId) {

        return notificationRepository
                .countByEmployeeIdAndStatusAndActiveTrue(
                        employeeId,
                        Notification.NotificationStatus.UNREAD
                );
    }


    private NotificationResponse toResponse(
            Notification notification) {

        return new NotificationResponse(
                notification.getId(),
                notification.getEmployeeId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getStatus(),
                notification.getReferenceId(),
                notification.getCreatedAt(),
                notification.getReadAt(),
                notification.getActive()
        );
    }
}