package org.example.handanddomain.domain.post.infra.jpa;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.handanddomain.domain.member.infra.jpa.MemberJpaEntity;
import org.example.handanddomain.domain.post.domain.Post;
import org.example.handanddomain.domain.post.domain.PostStatus;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MemberJpaEntity member;

    private String title;

    private String content;

    private PostStatus status;

    public static PostJpaEntity fromDomain(@NotNull Post post) {
        PostJpaEntity postJpaEntity = new PostJpaEntity();
        postJpaEntity.id = post.getId();
        postJpaEntity.member = new MemberJpaEntity(post.getMember());
        postJpaEntity.title = post.getTitle();
        postJpaEntity.content = post.getContent();
        postJpaEntity.status = post.getStatus();
        return postJpaEntity;
    }

    public Post toDomain() {
        return new Post(
                id,
                member.toDomain(),
                title,
                content,
                status
        );
    }

}
