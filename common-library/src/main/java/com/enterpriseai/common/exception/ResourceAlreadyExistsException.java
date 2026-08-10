package com.enterpriseai.common.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

    private final ErrorCode errorCode;

    public ResourceAlreadyExistsException(String message) {
        super(message);
        this.errorCode = ErrorCode.RESOURCE_ALREADY_EXISTS;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}