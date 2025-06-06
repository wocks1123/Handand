package org.example.handanddomain.domain.auth.application.port.in.dto;

public record LoginResult(
        String accessToken,
        String refreshToken,
        Long memberId
) {
}
