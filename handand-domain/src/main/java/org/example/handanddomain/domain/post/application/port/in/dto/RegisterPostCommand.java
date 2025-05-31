package org.example.handanddomain.domain.post.application.port.in.dto;

import org.example.handanddomain.domain.post.domain.PostStatus;

public record RegisterPostCommand(
        Long memberId,
        String title,
        String content,
        PostStatus status
) {
}
