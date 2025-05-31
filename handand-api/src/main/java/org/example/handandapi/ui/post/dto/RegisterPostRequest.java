package org.example.handandapi.ui.post.dto;

import org.example.handanddomain.domain.post.domain.PostStatus;

public record RegisterPostRequest(
        String title,
        String content,
        PostStatus status
) {
}
