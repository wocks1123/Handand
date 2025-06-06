package org.example.handanddomain.domain.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberAuthTest {

    @Test
    @DisplayName("회원 인증 객체를 생성한다.")
    void test01() {
        // given
        Long memberId = 1L;
        String username = "TEST_USER";
        String rawPassword = "TEST_PASSWORD";

        // when
        MemberAuth memberAuth = MemberAuth.withLocal(memberId, username, rawPassword);

        // then
        assertAll(
                () -> assertThat(memberAuth.getMemberId()).isEqualTo(memberId),
                () -> assertThat(memberAuth.getUsername()).isEqualTo(username),
                () -> assertThat(memberAuth.getPassword()).isNotNull(),
                () -> assertThat(memberAuth.getLoginType()).isEqualTo(LoginType.Local),
                () -> assertThat(memberAuth.getExternalId()).isNull()
        );
    }

    @Test
    @DisplayName("비밀번호를 변경한다.")
    void test02() {
        // given
        Long memberId = 1L;
        String username = "TEST_USER";
        String rawPassword = "TEST_PASSWORD";
        MemberAuth memberAuth = MemberAuth.withLocal(memberId, username, rawPassword);
        String newPassword = "NEW_TEST_PASSWORD";

        // when
        memberAuth.changePassword(newPassword);

        // then
        assertThat(memberAuth.getPassword()).isEqualTo(newPassword);
    }

}
