package org.example.handanddomain.domain.auth.application.port.in.dto;

public record RegisterMemberWithAuthCommand(
        String username,
        String password,
        String name,
        String profileImageUrl
) {
}
