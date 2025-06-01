package org.example.handandapi.common;

public record ApiErrorResponse(
        String errorMessage,
        int code
) {
}
