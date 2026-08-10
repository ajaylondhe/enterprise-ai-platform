package com.enterpriseai.document.dto;

import java.time.LocalDateTime;

public class DocumentResponse {

    private Long id;

    private Long employeeId;

    private String fileName;

    private String documentType;

    private String filePath;

    private Long fileSize;

    private String contentType;

    private LocalDateTime uploadedAt;

    private Boolean active;


    public DocumentResponse() {
    }


    public DocumentResponse(
            Long id,
            Long employeeId,
            String fileName,
            String documentType,
            String filePath,
            Long fileSize,
            String contentType,
            LocalDateTime uploadedAt,
            Boolean active) {

        this.id = id;
        this.employeeId = employeeId;
        this.fileName = fileName;
        this.documentType = documentType;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.uploadedAt = uploadedAt;
        this.active = active;
    }


    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getFilePath() {
        return filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public Boolean getActive() {
        return active;
    }
}