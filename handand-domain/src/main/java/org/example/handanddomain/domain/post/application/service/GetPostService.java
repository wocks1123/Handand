package org.example.handanddomain.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.post.application.exception.PostNotFoundException;
import org.example.handanddomain.domain.post.application.port.in.GetPostUseCase;
import org.example.handanddomain.domain.post.application.port.out.LoadPostPort;
import org.example.handanddomain.domain.post.domain.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GetPostService implements GetPostUseCase {

    private final LoadPostPort loadPostPort;

    @Override
    public @NotNull Post getPost(@NotNull Long postId) throws PostNotFoundException {
        return loadPostPort.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

}
