package org.example.handanddomain.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.post.application.exception.PostNotFoundException;
import org.example.handanddomain.domain.post.application.port.in.ModifyPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.dto.ModifyPostCommand;
import org.example.handanddomain.domain.post.application.port.out.LoadPostPort;
import org.example.handanddomain.domain.post.application.port.out.UpdatePostPort;
import org.example.handanddomain.domain.post.domain.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ModifyPostService implements ModifyPostUseCase {

    private final UpdatePostPort updatePostPort;
    private final LoadPostPort loadPostPort;
    private final GetMemberUseCase getMemberUseCase;

    @Override
    public void modifyPost(@NotNull ModifyPostCommand command) {
        Post post = loadPostPort.findById(command.postId())
                .orElseThrow(() -> new PostNotFoundException(command.postId()));

        post.modify(
                getMemberUseCase.getMember(command.memberId()),
                command.title(),
                command.content()
        );

        updatePostPort.update(post);
    }

}
