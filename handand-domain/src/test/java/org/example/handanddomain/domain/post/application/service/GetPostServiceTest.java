package org.example.handanddomain.domain.post.application.service;

import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.post.application.exception.PostNotFoundException;
import org.example.handanddomain.domain.post.domain.Post;
import org.example.handanddomain.domain.post.domain.PostStatus;
import org.example.handanddomain.domain.post.infra.PostFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetPostServiceTest {

    private GetPostService getPostService;
    private PostFakeRepository postFakeRepository;
    private final Member dummyMember = new Member(1L, "TEST_MEMBER", null);


    @BeforeEach
    void setup() {
        postFakeRepository = new PostFakeRepository();
        getPostService = new GetPostService(postFakeRepository);
    }

    @Test
    @DisplayName("id로 게시물을 조회한다.")
    void testGetPost() {
        // given
        var post = postFakeRepository.save(
                Post.builder()
                        .member(dummyMember)
                        .title("Test Title")
                        .content("Test Content")
                        .status(PostStatus.PUBLIC)
                        .build()
        );

        // when
        var retrievedPost = getPostService.getPost(post.getId());

        // then
        assertThat(retrievedPost).isNotNull();
        assertThat(retrievedPost.getId()).isEqualTo(post.getId());
        assertThat(retrievedPost.getMember().getId()).isEqualTo(dummyMember.getId());
        assertThat(retrievedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(retrievedPost.getContent()).isEqualTo(post.getContent());
    }

    @Test
    @DisplayName("존재하지 않는 id로 게시물을 조회하면 예외가 발생한다.")
    void test02() {
        // given
        Long nonExistentPostId = 999L;

        // when & then
        assertThrows(PostNotFoundException.class, () ->
                getPostService.getPost(nonExistentPostId)
        );
    }

}
