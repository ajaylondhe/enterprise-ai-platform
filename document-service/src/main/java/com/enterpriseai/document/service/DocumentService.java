package com.enterpriseai.document.service;

import com.enterpriseai.common.exception.ResourceNotFoundException;
import com.enterpriseai.document.dto.DocumentRequest;
import com.enterpriseai.document.dto.DocumentResponse;
import com.enterpriseai.document.entity.Document;
import com.enterpriseai.document.repository.DocumentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;


    public DocumentService(
            DocumentRepository documentRepository) {

        this.documentRepository = documentRepository;
    }


    @Transactional
    public DocumentResponse create(
            DocumentRequest request) {

        Document document = new Document();

        document.setEmployeeId(
                request.getEmployeeId()
        );

        document.setFileName(
                request.getFileName()
        );

        document.setDocumentType(
                request.getDocumentType()
        );

        document.setFilePath(
                request.getFilePath()
        );

        document.setFileSize(
                request.getFileSize()
        );

        document.setContentType(
                request.getContentType()
        );

        document.setActive(true);

        Document saved =
                documentRepository.save(document);

        return toResponse(saved);
    }


    @Transactional(readOnly = true)
    public List<DocumentResponse> getAll() {

        return documentRepository
                .findByActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public DocumentResponse getById(
            Long id) {

        Document document =
                documentRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Document not found with id: " + id
                                )
                        );

        return toResponse(document);
    }


    @Transactional(readOnly = true)
    public List<DocumentResponse> getByEmployee(
            Long employeeId) {

        return documentRepository
                .findByEmployeeIdAndActiveTrue(
                        employeeId
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<DocumentResponse> getByType(
            String documentType) {

        return documentRepository
                .findByDocumentTypeAndActiveTrue(
                        documentType
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Transactional
    public void delete(Long id) {

        Document document =
                documentRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Document not found with id: " + id
                                )
                        );

        document.setActive(false);

        documentRepository.save(document);
    }


    private DocumentResponse toResponse(
            Document document) {

        return new DocumentResponse(
                document.getId(),
                document.getEmployeeId(),
                document.getFileName(),
                document.getDocumentType(),
                document.getFilePath(),
                document.getFileSize(),
                document.getContentType(),
                document.getUploadedAt(),
                document.getActive()
        );
    }
}