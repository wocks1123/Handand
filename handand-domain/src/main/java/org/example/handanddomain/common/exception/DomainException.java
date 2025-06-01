package org.example.handanddomain.common.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorMessage;

    public DomainException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = null;
    }

    public DomainException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public int getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public String getErrorMessage() {
        if (errorMessage != null) {
            return String.format("%s: %s", errorCode.getMessage(), errorMessage);
        }
        return errorCode.getMessage();
    }

}
