package org.example.handandapi.ui.auth.dto;

public record LoginRequest(
        String username,
        String password
) {
}
