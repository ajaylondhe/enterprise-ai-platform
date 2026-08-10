package com.enterpriseai.notification.controller;

import com.enterpriseai.common.api.ApiResponse;
import com.enterpriseai.notification.dto.NotificationRequest;
import com.enterpriseai.notification.dto.NotificationResponse;
import com.enterpriseai.notification.entity.Notification;
import com.enterpriseai.notification.service.NotificationService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;


    public NotificationController(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }


    @PostMapping
    public ResponseEntity<
            ApiResponse<NotificationResponse>> create(

            @Valid
            @RequestBody
            NotificationRequest request) {

        NotificationResponse response =
                notificationService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Notification created successfully",
                                response
                        )
                );
    }


    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<
            ApiResponse<Page<NotificationResponse>>>
    getEmployeeNotifications(

            @PathVariable Long employeeId,
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notifications retrieved successfully",

                        notificationService
                                .getEmployeeNotifications(
                                        employeeId,
                                        pageable
                                )
                )
        );
    }


    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<
            ApiResponse<Page<NotificationResponse>>>
    getByStatus(

            @PathVariable Long employeeId,

            @PathVariable
            Notification.NotificationStatus status,

            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notifications retrieved successfully",

                        notificationService
                                .getByStatus(
                                        employeeId,
                                        status,
                                        pageable
                                )
                )
        );
    }


    @PatchMapping("/{id}/read")
    public ResponseEntity<
            ApiResponse<NotificationResponse>>
    markAsRead(

            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification marked as read",

                        notificationService
                                .markAsRead(id)
                )
        );
    }


    @GetMapping("/employee/{employeeId}/unread-count")
    public ResponseEntity<ApiResponse<Long>>
    unreadCount(

            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Unread notification count retrieved successfully",

                        notificationService
                                .countUnread(employeeId)
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        notificationService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification deleted successfully",
                        null
                )
        );
    }
}