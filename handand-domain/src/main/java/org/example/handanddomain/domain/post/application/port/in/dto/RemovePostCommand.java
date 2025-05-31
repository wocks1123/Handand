package org.example.handanddomain.domain.post.application.port.in.dto;

public record RemovePostCommand(
        Long postId,
        Long memberId
) {
}
