package org.example.handanddomain.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.post.application.exception.PostNotFoundException;
import org.example.handanddomain.domain.post.application.exception.PostOwnershipException;
import org.example.handanddomain.domain.post.application.port.in.RemovePostUseCase;
import org.example.handanddomain.domain.post.application.port.in.dto.RemovePostCommand;
import org.example.handanddomain.domain.post.application.port.out.DeletePostPort;
import org.example.handanddomain.domain.post.application.port.out.LoadPostPort;
import org.example.handanddomain.domain.post.domain.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
class RemovePostService implements RemovePostUseCase {

    private final DeletePostPort deletePostPort;
    private final LoadPostPort loadPostPort;
    private final GetMemberUseCase getMemberUseCase;


    @Override
    public void removePost(@NotNull RemovePostCommand command) {

        Member member = getMemberUseCase.getMember(command.memberId());
        Post post = loadPostPort.findById(command.postId())
                .orElseThrow(() -> new PostNotFoundException(command.postId()));

        if (!post.getMember().equals(member)) {
            throw new PostOwnershipException();
        }

        deletePostPort.deleteById(command.postId());
    }

}
