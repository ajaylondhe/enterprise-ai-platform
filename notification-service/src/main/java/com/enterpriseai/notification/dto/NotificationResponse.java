package com.enterpriseai.notification.dto;

import com.enterpriseai.notification.entity.Notification;

import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;

    private Long employeeId;

    private String title;

    private String message;

    private Notification.NotificationType type;

    private Notification.NotificationStatus status;

    private Long referenceId;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    private Boolean active;


    public NotificationResponse(
            Long id,
            Long employeeId,
            String title,
            String message,
            Notification.NotificationType type,
            Notification.NotificationStatus status,
            Long referenceId,
            LocalDateTime createdAt,
            LocalDateTime readAt,
            Boolean active) {

        this.id = id;
        this.employeeId = employeeId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.status = status;
        this.referenceId = referenceId;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.active = active;
    }


    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Notification.NotificationType getType() {
        return type;
    }

    public Notification.NotificationStatus getStatus() {
        return status;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public Boolean getActive() {
        return active;
    }
}