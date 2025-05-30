package org.example.handanddomain.domain.member.application.port.in.dto;

public record ModifyMemberCommand(
        Long memberId,
        String profileImageUrl
) {
}
