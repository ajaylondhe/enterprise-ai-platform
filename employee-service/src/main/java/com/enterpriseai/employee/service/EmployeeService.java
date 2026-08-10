package com.enterpriseai.employee.service;

import com.enterpriseai.employee.dto.EmployeeRequest;
import com.enterpriseai.employee.dto.EmployeeResponse;
import com.enterpriseai.employee.entity.Employee;
import com.enterpriseai.employee.repository.EmployeeRepository;

import com.enterpriseai.common.exception.ResourceAlreadyExistsException;
import com.enterpriseai.common.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public EmployeeResponse create(
            EmployeeRequest request) {

        if (employeeRepository.existsByEmail(
                request.getEmail())) {

            throw new ResourceAlreadyExistsException(
                    "Employee already exists with email: "
                            + request.getEmail()
            );
        }

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(
                request.getDepartment()
        );
        employee.setDesignation(
                request.getDesignation()
        );
        employee.setSalary(
                request.getSalary()
        );

        Employee saved =
                employeeRepository.save(employee);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAll() {

        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getById(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Employee not found with id: "
                                                + id
                                )
                        );

        return toResponse(employee);
    }

    @Transactional
    public EmployeeResponse update(
            Long id,
            EmployeeRequest request) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Employee not found with id: "
                                                + id
                                )
                        );

        if (!employee.getEmail()
                .equalsIgnoreCase(request.getEmail())
                && employeeRepository.existsByEmail(
                request.getEmail())) {

            throw new ResourceAlreadyExistsException(
                    "Employee already exists with email: "
                            + request.getEmail()
            );
        }

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(
                request.getDepartment()
        );
        employee.setDesignation(
                request.getDesignation()
        );
        employee.setSalary(
                request.getSalary()
        );

        return toResponse(
                employeeRepository.save(employee)
        );
    }

    @Transactional
    public void delete(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Employee not found with id: "
                                                + id
                                )
                        );

        employeeRepository.delete(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getByDepartment(
            String department) {

        return employeeRepository
                .findByDepartmentIgnoreCase(department)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private EmployeeResponse toResponse(
            Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getSalary(),
                employee.getActive(),
                employee.getCreatedAt()
        );
    }
}