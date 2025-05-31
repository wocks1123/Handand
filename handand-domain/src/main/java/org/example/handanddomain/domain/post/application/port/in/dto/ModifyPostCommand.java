package org.example.handanddomain.domain.post.application.port.in.dto;

public record ModifyPostCommand(
        Long postId,
        Long memberId,
        String title,
        String content
) {
}
