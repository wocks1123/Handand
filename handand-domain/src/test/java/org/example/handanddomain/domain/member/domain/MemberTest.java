package org.example.handanddomain.domain.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberTest {

    @Test
    @DisplayName("회원을 생성한다.")
    void test01() {
        // given
        Long id = 1L;
        String name = "TEST_NAME";
        String profileImageUrl = "image://TEST_IMAGE_URL";

        // when
        Member member = new Member(id, name, profileImageUrl);

        // then
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(id),
                () -> assertThat(member.getName()).isEqualTo(name),
                () -> assertThat(member.getProfileImageUrl()).isEqualTo(profileImageUrl)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("프로필 사진을 지정하지 않으면 기본 프로필이 설정된다.")
    void test02(String profileImageUrl) {
        // given
        Long id = 1L;
        String name = "TEST_NAME";

        // when
        Member member = new Member(id, name, profileImageUrl);

        // then
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(id),
                () -> assertThat(member.getName()).isEqualTo(name),
                () -> assertThat(member.getProfileImageUrl()).isEqualTo(Member.DEFAULT_PROFILE_IMAGE_URL)
        );
    }

    @Nested
    class MemberObjectTest {

        @Test
        @DisplayName("동일한 ID를 가진 회원 객체는 동일하다.")
        void test01() {
            // given
            Long id = 1L;
            String name = "TEST_NAME";
            Member member1 = new Member(id, name, null);
            Member member2 = new Member(id, name, null);

            // when & then
            assertAll(
                    () -> assertThat(member1).isEqualTo(member2),
                    () -> assertThat(member1).hasSameHashCodeAs(member2)
            );
        }

        @Test
        @DisplayName("다른 ID를 가진 회원 객체는 동일하지 않다.")
        void test02() {
            // given
            Long id1 = 1L;
            Long id2 = 2L;
            String name = "TEST_NAME";
            Member member1 = new Member(id1, name, null);
            Member member2 = new Member(id2, name, null);

            // when & then
            assertAll(
                    () -> assertThat(member1).isNotEqualTo(member2),
                    () -> assertThat(member1.hashCode()).isNotEqualTo(member2.hashCode())
            );
        }


    }

}
