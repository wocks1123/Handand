package org.example.handandapi.common;

import org.example.handanddomain.common.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DomainException.class})
    public ResponseEntity<ApiErrorResponse> handleDomainException(DomainException exception) {
        MediaType contentType = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
        HttpStatus status = HttpStatus.valueOf(exception.getHttpStatus());
        ApiErrorResponse body = new ApiErrorResponse(exception.getErrorMessage(), exception.getHttpStatus());

        return ResponseEntity.status(status)
                .contentType(contentType)
                .body(body);
    }

}
