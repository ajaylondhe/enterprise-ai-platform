package com.enterpriseai.document.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "documents",
        indexes = {
                @Index(
                        name = "idx_document_employee",
                        columnList = "employee_id"
                ),
                @Index(
                        name = "idx_document_type",
                        columnList = "document_type"
                )
        }
)
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "employee_id",
            nullable = false
    )
    private Long employeeId;

    @Column(
            name = "file_name",
            nullable = false
    )
    private String fileName;

    @Column(
            name = "document_type",
            nullable = false
    )
    private String documentType;

    @Column(
            name = "file_path",
            nullable = false
    )
    private String filePath;

    @Column(
            name = "file_size"
    )
    private Long fileSize;

    @Column(
            name = "content_type"
    )
    private String contentType;

    @Column(
            name = "uploaded_at",
            nullable = false
    )
    private LocalDateTime uploadedAt;

    @Column(
            name = "active",
            nullable = false
    )
    private Boolean active = true;


    @PrePersist
    protected void onCreate() {

        uploadedAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}