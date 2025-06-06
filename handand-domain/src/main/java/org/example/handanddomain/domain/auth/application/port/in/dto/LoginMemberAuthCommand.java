package org.example.handanddomain.domain.auth.application.port.in.dto;

public record LoginMemberAuthCommand(
        String username,
        String password
) {
}
