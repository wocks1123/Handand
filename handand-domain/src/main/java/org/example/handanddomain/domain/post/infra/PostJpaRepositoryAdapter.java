package org.example.handanddomain.domain.post.infra;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.post.application.port.out.DeletePostPort;
import org.example.handanddomain.domain.post.application.port.out.LoadPostPort;
import org.example.handanddomain.domain.post.application.port.out.SavePostPort;
import org.example.handanddomain.domain.post.application.port.out.UpdatePostPort;
import org.example.handanddomain.domain.post.domain.Post;
import org.example.handanddomain.domain.post.infra.jpa.PostJpaEntity;
import org.example.handanddomain.domain.post.infra.jpa.PostJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class PostJpaRepositoryAdapter implements SavePostPort, LoadPostPort, UpdatePostPort, DeletePostPort {

    private final PostJpaRepository postJpaRepository;

    @Override
    public @NotNull Post save(@NotNull Post post) {
        return postJpaRepository.save(PostJpaEntity.fromDomain(post)).toDomain();
    }

    @Override
    public Optional<Post> findById(@NotNull Long postId) {
        return postJpaRepository.findById(postId)
                .map(PostJpaEntity::toDomain);
    }

    @Override
    public void update(@NotNull Post post) {
        // do nothing
    }

    @Override
    public void deleteById(@NotNull Long postId) {
        postJpaRepository.deleteById(postId);
    }
}
