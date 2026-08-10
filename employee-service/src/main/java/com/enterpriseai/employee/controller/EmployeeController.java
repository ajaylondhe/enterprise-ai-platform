package com.enterpriseai.employee.controller;

import com.enterpriseai.common.api.ApiResponse;
import com.enterpriseai.employee.dto.EmployeeRequest;
import com.enterpriseai.employee.dto.EmployeeResponse;
import com.enterpriseai.employee.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(
            EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> create(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response =
                employeeService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Employee created successfully",
                                response
                        )
                );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employees retrieved successfully",
                        employeeService.getAll()
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee retrieved successfully",
                        employeeService.getById(id)
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee updated successfully",
                        employeeService.update(id, request)
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        employeeService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee deleted successfully",
                        null
                )
        );
    }

    @GetMapping("/department/{department}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>>
    getByDepartment(
            @PathVariable String department) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employees retrieved successfully",
                        employeeService.getByDepartment(
                                department
                        )
                )
        );
    }
}