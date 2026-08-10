package com.enterpriseai.document.repository;

import com.enterpriseai.document.entity.Document;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository
        extends JpaRepository<Document, Long> {

    List<Document> findByEmployeeIdAndActiveTrue(
            Long employeeId
    );

    List<Document> findByDocumentTypeAndActiveTrue(
            String documentType
    );

    List<Document> findByActiveTrue();
}