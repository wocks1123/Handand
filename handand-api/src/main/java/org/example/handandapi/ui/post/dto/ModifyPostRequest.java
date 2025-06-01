package org.example.handandapi.ui.post.dto;

public record ModifyPostRequest(
        Long memberId,
        String title,
        String content
) {
}
