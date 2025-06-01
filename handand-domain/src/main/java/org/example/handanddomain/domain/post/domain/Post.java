package org.example.handanddomain.domain.post.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.handanddomain.common.exception.DomainIllegalArgumentException;
import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.post.application.exception.PostOwnershipException;

@Getter
public class Post {

    private final Long id;
    private final Member member;
    private String title;
    private String content;
    private PostStatus status;

    @Builder
    public Post(Member member, String title, String content, PostStatus status) {
        this(null, member, title, content, status);
    }

    @Builder
    public Post(Long id, Member member, String title, String content, PostStatus status) {
        validatePost(member, title, content);

        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    private void validatePost(Member member, String title, String content) {
        if (member == null) {
            throw new DomainIllegalArgumentException("회원을 지정해야 합니다.");
        }
        if (title == null || title.isEmpty()) {
            throw new DomainIllegalArgumentException("제목을 입력해야 합니다.");
        }
        if (content == null || content.isEmpty()) {
            throw new DomainIllegalArgumentException("내용을 입력해야 합니다.");
        }
    }

    public void modify(Member member, String title, String content) {
        validatePost(member, title, content);

        if (!this.member.equals(member)) {
            throw new PostOwnershipException();
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
