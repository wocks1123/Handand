package org.example.handanddomain.domain.post.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.handanddomain.domain.member.domain.Member;

@Getter
public class Post {

    private final Long id;
    private final Member member;
    private String title;
    private String content;
    private PostStatus status;

    @Builder
    public Post(Member member, String title, String content, PostStatus status) {
        if (member == null) {
            throw new IllegalArgumentException("Member should not be null");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title should not be null or empty");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content should not be null or empty");
        }

        this.id = null;
        this.member = member;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public Post(Long id, Member member, String title, String content, PostStatus status) {
        if (member == null) {
            throw new IllegalArgumentException("Member should not be null");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title should not be null or empty");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content should not be null or empty");
        }

        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public void modify(Member member, String title, String content) {
        if (member == null) {
            throw new IllegalArgumentException("Member should not be null");
        }
        if (!this.member.equals(member)) {
            throw new IllegalArgumentException("Only the author can modify the post");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title should not be null or empty");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content should not be null or empty");
        }

        this.title = title;
        this.content = content;
    }

    public void open() {
        this.status = PostStatus.PUBLIC;
    }

    public void hide() {
        this.status = PostStatus.PRIVATE;
    }

}
