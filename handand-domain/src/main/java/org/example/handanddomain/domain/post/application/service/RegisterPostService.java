package org.example.handanddomain.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.post.application.port.in.RegisterPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.dto.RegisterPostCommand;
import org.example.handanddomain.domain.post.application.port.out.SavePostPort;
import org.example.handanddomain.domain.post.domain.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterPostService implements RegisterPostUseCase {

    private final SavePostPort savePostPort;
    private final GetMemberUseCase getMemberUseCase;

    @Override
    public @NotNull Long registerPost(@NotNull RegisterPostCommand command) {

        Member member = getMemberUseCase.getMember(command.memberId());

        Post newPost = Post.builder()
                .member(member)
                .title(command.title())
                .content(command.content())
                .status(command.status())
                .build();

        Post savedPost = savePostPort.save(newPost);

        return savedPost.getId();
    }

}
