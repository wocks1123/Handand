package org.example.handanddomain.domain.member.application.port.in.dto;

public record RegisterMemberCommand(
        String name,
        String profileImageUrl
) {
}
