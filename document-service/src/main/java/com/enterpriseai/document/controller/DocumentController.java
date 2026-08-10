package com.enterpriseai.document.controller;

import com.enterpriseai.common.api.ApiResponse;
import com.enterpriseai.document.dto.DocumentRequest;
import com.enterpriseai.document.dto.DocumentResponse;
import com.enterpriseai.document.service.DocumentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;


    public DocumentController(
            DocumentService documentService) {

        this.documentService = documentService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<DocumentResponse>> create(
            @Valid @RequestBody DocumentRequest request) {

        DocumentResponse response =
                documentService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Document created successfully",
                                response
                        )
                );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Documents retrieved successfully",
                        documentService.getAll()
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Document retrieved successfully",
                        documentService.getById(id)
                )
        );
    }


    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>>
    getByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee documents retrieved successfully",
                        documentService.getByEmployee(
                                employeeId
                        )
                )
        );
    }


    @GetMapping("/type/{documentType}")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>>
    getByType(
            @PathVariable String documentType) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Documents retrieved successfully",
                        documentService.getByType(
                                documentType
                        )
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        documentService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Document deleted successfully",
                        null
                )
        );
    }
}