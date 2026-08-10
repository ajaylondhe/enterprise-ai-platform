package com.enterpriseai.employee.dto;

import java.time.LocalDateTime;

public class EmployeeResponse {

    private Long id;
    private String name;
    private String email;
    private String department;
    private String designation;
    private Double salary;
    private Boolean active;
    private LocalDateTime createdAt;

    public EmployeeResponse(
            Long id,
            String name,
            String email,
            String department,
            String designation,
            Double salary,
            Boolean active,
            LocalDateTime createdAt) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public Double getSalary() {
        return salary;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}