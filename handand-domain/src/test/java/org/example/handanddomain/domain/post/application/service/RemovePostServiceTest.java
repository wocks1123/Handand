package org.example.handanddomain.domain.post.application.service;

import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.MemberUseCaseFactory;
import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.post.application.port.in.dto.RemovePostCommand;
import org.example.handanddomain.domain.post.domain.Post;
import org.example.handanddomain.domain.post.domain.PostStatus;
import org.example.handanddomain.domain.post.infra.PostFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RemovePostServiceTest {

    private RemovePostService removePostService;
    private PostFakeRepository postFakeRepository;

    private final Member dummyMember = new Member(1L, "TEST_MEMBER", null);
    private final Member anotherMember = new Member(2L, "ANOTHER_MEMBER", null);
    private Post givenPost;


    @BeforeEach
    void setup() {
        postFakeRepository = new PostFakeRepository();
        GetMemberUseCase getMemberUseCaseStub = MemberUseCaseFactory.createFakeGetMemberUseCase(
                dummyMember, anotherMember
        );
        removePostService = new RemovePostService(
                postFakeRepository,
                postFakeRepository,
                getMemberUseCaseStub
        );

        givenPost = postFakeRepository.save(
                Post.builder()
                        .member(dummyMember)
                        .title("Initial Title")
                        .content("Initial Content")
                        .status(PostStatus.PUBLIC)
                        .build()
        );
    }


    @Test
    @DisplayName("게시물 삭제에 성공한다.")
    void test01() {
        // given
        var command = new RemovePostCommand(
                givenPost.getId(),
                dummyMember.getId()
        );

        // when
        removePostService.removePost(command);

        // then
        assertThat(postFakeRepository.findById(givenPost.getId())).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 게시물 삭제 시 예외가 발생한다.")
    void test02() {
        // given
        var command = new RemovePostCommand(
                999L,
                dummyMember.getId()
        );

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            removePostService.removePost(command);
        });
    }

    @Test
    @DisplayName("다른 회원의 게시물 삭제 시 예외가 발생한다.")
    void test03() {
        // given
        var command = new RemovePostCommand(
                givenPost.getId(),
                anotherMember.getId()
        );

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            removePostService.removePost(command);
        });
    }

}
