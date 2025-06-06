package org.example.handandapi.ui.auth.dto;

public record RegisterMemberWithAuthRequest(
        String username,
        String password,
        String name,
        String profileImageUrl
) {
}
