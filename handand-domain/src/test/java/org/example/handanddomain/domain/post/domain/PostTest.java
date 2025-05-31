package org.example.handanddomain.domain.post.domain;

import org.example.handanddomain.domain.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostTest {

    private final Member dummyMember = new Member(1L, "TEST_MEMBER", null);

    @Test
    @DisplayName("게시물을 생성한다.")
    void test01() {
        // given
        Post post = new Post(1L, dummyMember, "제목", "내용", PostStatus.PUBLIC);

        // then
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getMember()).isEqualTo(dummyMember);
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
        assertThat(post.getStatus()).isEqualTo(PostStatus.PUBLIC);
    }

    @Test
    @DisplayName("작성자를 지정하지 않으면 오류가 발생한다.")
    void test02() {
        // given
        Member givenMember = null;

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            new Post(1L, givenMember, "제목", "내용", PostStatus.PUBLIC);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("게시물 제목이 비어있으면 오류가 발생한다.")
    void test03(String title) {
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            new Post(1L, dummyMember, title, "내용", PostStatus.PUBLIC);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("게시물 내용이 비어있으면 오류가 발생한다.")
    void test04(String content) {
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            new Post(1L, dummyMember, "제목", content, PostStatus.PUBLIC);
        });
    }

    @Test
    @DisplayName("게시물을 수정한다.")
    void test05() {
        // given
        Post post = new Post(1L, dummyMember, "제목", "내용", PostStatus.PUBLIC);
        String newTitle = "새 제목";
        String newContent = "새 내용";

        // when
        post.modify(dummyMember, newTitle, newContent);

        // then
        assertAll(
                () -> assertThat(post.getTitle()).isEqualTo(newTitle),
                () -> assertThat(post.getContent()).isEqualTo(newContent)
        );
    }

    @Test
    @DisplayName("작성자가 아닌 사용자가 게시물을 수정하려고 하면 오류가 발생한다.")
    void test06() {
        // given
        Post post = new Post(1L, dummyMember, "제목", "내용", PostStatus.PUBLIC);
        Member otherMember = new Member(2L, "OTHER_MEMBER", null);
        String newTitle = "새 제목";
        String newContent = "새 내용";

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            post.modify(otherMember, newTitle, newContent);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("게시물 제목을 수정할 때 비어있으면 오류가 발생한다.")
    void test07(String title) {
        // given
        Post post = new Post(1L, dummyMember, "제목", "내용", PostStatus.PUBLIC);

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            post.modify(dummyMember, title, "새 내용");
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("게시물 내용을 수정할 때 비어있으면 오류가 발생한다.")
    void test08(String content) {
        // given
        Post post = new Post(1L, dummyMember, "제목", "내용", PostStatus.PUBLIC);

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            post.modify(dummyMember, "새 제목", content);
        });
    }

    @Test
    @DisplayName("게시물을 공개 상태로 변경한다.")
    void test09() {
        // given
        Post post = new Post(1L, dummyMember, "제목", "내용", PostStatus.PRIVATE);

        // when
        post.open();

        // then
        assertThat(post.getStatus()).isEqualTo(PostStatus.PUBLIC);
    }

    @Test
    @DisplayName("게시물을 비공개 상태로 변경한다.")
    void test10() {
        // given
        Post post = new Post(1L, dummyMember, "제목", "내용", PostStatus.PUBLIC);

        // when
        post.hide();

        // then
        assertThat(post.getStatus()).isEqualTo(PostStatus.PRIVATE);
    }

}
