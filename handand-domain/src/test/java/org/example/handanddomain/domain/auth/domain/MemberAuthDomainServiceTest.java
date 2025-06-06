package org.example.handanddomain.domain.auth.domain;

import org.example.handanddomain.domain.auth.infra.FakePasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberAuthDomainServiceTest {


    private MemberAuthDomainService memberAuthDomainService;
    private FakePasswordEncoder fakePasswordEncoder;

    @BeforeEach
    void setup() {
        fakePasswordEncoder = new FakePasswordEncoder();
        memberAuthDomainService = new MemberAuthDomainService(fakePasswordEncoder);
    }


    @Test
    @DisplayName("회원 인증 객체가 올바르게 생성된다.")
    void test01() {
        // given
        Long memberId = 1L;
        String username = "TEST_USER";
        String rawPassword = "TEST_PASSWORD";

        // when
        MemberAuth memberAuth = memberAuthDomainService.createMemberAuth(memberId, username, rawPassword);

        // then
        assertAll(
                () -> assertThat(memberAuth.getMemberId()).isEqualTo(memberId),
                () -> assertThat(memberAuth.getUsername()).isEqualTo(username),
                () -> assertThat(memberAuth.getPassword()).isEqualTo(fakePasswordEncoder.encode(rawPassword)),
                () -> assertThat(fakePasswordEncoder.matches(rawPassword, memberAuth.getPassword())).isTrue(),
                () -> assertThat(memberAuth.getLoginType()).isEqualTo(LoginType.Local),
                () -> assertThat(memberAuth.getExternalId()).isNull()
        );
    }

    @Test
    @DisplayName("비밀번호가 올바르게 변경된다.")
    void test02() {
        // given
        MemberAuth memberAuth = MemberAuth.withLocal(1L, "TEST_USER", "TEST_PASSWORD");
        String newRawPassword = "NEW_TEST_PASSWORD";

        // when
        memberAuthDomainService.changePassword(memberAuth, newRawPassword);

        // then
        assertAll(
                () -> assertThat(memberAuth.getPassword()).isEqualTo(fakePasswordEncoder.encode(newRawPassword)),
                () -> assertThat(fakePasswordEncoder.matches(newRawPassword, memberAuth.getPassword())).isTrue()
        );
    }

    @Test
    @DisplayName("비밀번호 확인이 올바르게 작동한다.")
    void test03() {
        // given
        MemberAuth memberAuth = memberAuthDomainService.createMemberAuth(1L, "TEST_USER", "TEST_PASSWORD");
        String correctPassword = "TEST_PASSWORD";
        String incorrectPassword = "WRONG_PASSWORD";

        // when & then
        assertAll(
                () -> assertThat(memberAuthDomainService.checkPassword(memberAuth, correctPassword)).isTrue(),
                () -> assertThat(memberAuthDomainService.checkPassword(memberAuth, incorrectPassword)).isFalse()
        );
    }

}
