package org.example.handanddomain.domain.post.application.service;

import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.MemberUseCaseFactory;
import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.post.application.exception.PostNotFoundException;
import org.example.handanddomain.domain.post.application.port.in.dto.ModifyPostCommand;
import org.example.handanddomain.domain.post.domain.Post;
import org.example.handanddomain.domain.post.domain.PostStatus;
import org.example.handanddomain.domain.post.infra.PostFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModifyPostServiceTest {

    private ModifyPostService modifyPostService;
    private PostFakeRepository postFakeRepository;
    private final Member dummyMember = new Member(1L, "TEST_MEMBER", null);

    private Post givenPost;


    @BeforeEach
    void setup() {
        postFakeRepository = new PostFakeRepository();
        GetMemberUseCase getMemberUseCaseStub = MemberUseCaseFactory.createFakeGetMemberUseCase(dummyMember);
        modifyPostService = new ModifyPostService(
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
    @DisplayName("게시물 수정에 성공한다.")
    void test01() {
        // given
        var command = new ModifyPostCommand(
                givenPost.getId(),
                dummyMember.getId(),
                "Updated Title",
                "Updated Content"
        );

        // when
        modifyPostService.modifyPost(command);

        // then
        Post foundPost = postFakeRepository.findById(givenPost.getId()).orElse(null);
        assertThat(foundPost).isNotNull();
        assertAll(
                () -> assertThat(foundPost.getId()).isEqualTo(givenPost.getId()),
                () -> assertThat(foundPost.getMember().getId()).isEqualTo(dummyMember.getId()),
                () -> assertThat(foundPost.getTitle()).isEqualTo(command.title()),
                () -> assertThat(foundPost.getContent()).isEqualTo(command.content())
        );
    }

    @Test
    @DisplayName("등록되지 않은 게시물을 수정하면 오류가 발생한다.")
    void test02() {
        // given
        var command = new ModifyPostCommand(
                999L,
                dummyMember.getId(),
                "Updated Title",
                "Updated Content"
        );

        assertThrows(PostNotFoundException.class, () -> {
            modifyPostService.modifyPost(command);
        });
    }

}
