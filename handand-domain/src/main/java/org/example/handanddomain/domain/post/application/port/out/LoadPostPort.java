package org.example.handanddomain.domain.post.application.port.out;

import org.example.handanddomain.domain.post.domain.Post;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface LoadPostPort {

    Optional<Post> findById(@NotNull Long postId);

}
