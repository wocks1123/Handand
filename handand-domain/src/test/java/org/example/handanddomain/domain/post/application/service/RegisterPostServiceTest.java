package org.example.handanddomain.domain.post.application.service;

import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.MemberUseCaseFactory;
import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.post.application.port.in.dto.RegisterPostCommand;
import org.example.handanddomain.domain.post.domain.Post;
import org.example.handanddomain.domain.post.domain.PostStatus;
import org.example.handanddomain.domain.post.infra.PostFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RegisterPostServiceTest {

    private RegisterPostService registerPostService;
    private PostFakeRepository postFakeRepository;
    private final Member dummyMember = new Member(1L, "TEST_MEMBER", null);

    @BeforeEach
    void setup() {
        postFakeRepository = new PostFakeRepository();
        GetMemberUseCase getMemberUseCaseStub = MemberUseCaseFactory.createFakeGetMemberUseCase(dummyMember);
        registerPostService = new RegisterPostService(
                postFakeRepository,
                getMemberUseCaseStub
        );
    }

    @Test
    @DisplayName("게시물 등록에 성공한다.")
    void test01() {
        // given
        var command = new RegisterPostCommand(
                1L,
                "게시물 제목",
                "게시물 내용",
                PostStatus.PUBLIC
        );

        // when
        Long postId = registerPostService.registerPost(command);

        // then
        Post foundPost = postFakeRepository.findById(postId).orElse(null);
        assertThat(foundPost).isNotNull();
        assertAll(
                () -> assertThat(foundPost.getId()).isEqualTo(postId),
                () -> assertThat(foundPost.getMember().getId()).isEqualTo(dummyMember.getId()),
                () -> assertThat(foundPost.getTitle()).isEqualTo(command.title()),
                () -> assertThat(foundPost.getContent()).isEqualTo(command.content()),
                () -> assertThat(foundPost.getStatus()).isEqualTo(command.status())
        );
    }


}
