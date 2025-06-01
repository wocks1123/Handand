package org.example.handanddomain.domain.post.application.port.in;

import org.example.handanddomain.domain.post.application.exception.PostNotFoundException;
import org.example.handanddomain.domain.post.domain.Post;
import org.jetbrains.annotations.NotNull;

public interface GetPostUseCase {

    @NotNull Post getPost(@NotNull Long postId) throws PostNotFoundException;

}
