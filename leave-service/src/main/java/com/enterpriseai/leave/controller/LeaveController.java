package com.enterpriseai.leave.controller;

import com.enterpriseai.common.api.ApiResponse;

import com.enterpriseai.leave.dto.LeaveRequest;
import com.enterpriseai.leave.dto.LeaveResponse;

import com.enterpriseai.leave.service.LeaveService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(
            LeaveService leaveService) {

        this.leaveService = leaveService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<LeaveResponse>> create(
            @Valid @RequestBody LeaveRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Leave created successfully",
                                leaveService.create(request)
                        )
                );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leaves retrieved successfully",
                        leaveService.getAll()
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<LeaveResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave retrieved successfully",
                        leaveService.getById(id)
                )
        );
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getMyLeaves(
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Your leaves retrieved successfully",
                        leaveService.getMyLeaves(
                                authentication.getName()
                        )
                )
        );
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LeaveResponse>> approve(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave approved successfully",
                        leaveService.approve(
                                id,
                                authentication.getName()
                        )
                )
        );
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LeaveResponse>> reject(
            @PathVariable Long id,
            @RequestParam String reason) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave rejected successfully",
                        leaveService.reject(
                                id,
                                reason
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        leaveService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave deleted successfully",
                        null
                )
        );
    }
}