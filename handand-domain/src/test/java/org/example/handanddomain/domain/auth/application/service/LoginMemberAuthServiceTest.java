package org.example.handanddomain.domain.auth.application.service;

import org.example.handanddomain.domain.auth.application.exception.LoginFailException;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginMemberAuthCommand;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginResult;
import org.example.handanddomain.domain.auth.application.port.out.TokenPort;
import org.example.handanddomain.domain.auth.domain.MemberAuth;
import org.example.handanddomain.domain.auth.domain.MemberAuthDomainService;
import org.example.handanddomain.domain.auth.infra.FakeMemberAuthRepository;
import org.example.handanddomain.domain.auth.infra.FakePasswordEncoder;
import org.example.handanddomain.domain.auth.infra.FakeTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginMemberAuthServiceTest {

    private LoginMemberAuthService loginMemberAuthService;
    private TokenPort tokenPort;

    @BeforeEach
    void setup() {
        FakeMemberAuthRepository fakeMemberAuthRepository = new FakeMemberAuthRepository();
        MemberAuthDomainService memberAuthDomainService = new MemberAuthDomainService(new FakePasswordEncoder());
        tokenPort = new FakeTokenService();
        loginMemberAuthService = new LoginMemberAuthService(fakeMemberAuthRepository, memberAuthDomainService, tokenPort);

        // 테스트 회원 등록
        MemberAuth givenMember = memberAuthDomainService.createMemberAuth(1L, "TEST_MEMBER", "TEST_PASSWORD");
        fakeMemberAuthRepository.save(givenMember);
    }

    @Test
    @DisplayName("올바른 계정과 비밀번호로 로그인에 성공한다.")
    void test01() {
        // given
        LoginMemberAuthCommand command = new LoginMemberAuthCommand("TEST_MEMBER", "TEST_PASSWORD");

        // when
        LoginResult result = loginMemberAuthService.login(command);

        // then
        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.memberId()).isEqualTo(1L),
                () -> assertThat(tokenPort.isValidateToken(result.accessToken())).isTrue(),
                () -> assertThat(tokenPort.isValidateToken(result.refreshToken())).isTrue(),
                () -> assertThat(tokenPort.extractUsername(result.accessToken())).isEqualTo(command.username()),
                () -> assertThat(result.memberId()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("등록되지 않는 계정으로 로그인을 시도하면 예외가 발생한다.")
    void test02() {
        // given
        LoginMemberAuthCommand command = new LoginMemberAuthCommand("NOT_EXIST_MEMBER", "TEST_PASSWORD");

        // when & then
        assertThrows(LoginFailException.class, () ->
                loginMemberAuthService.login(command)
        );
    }

    @Test
    @DisplayName("비밀번호가 틀리면 예외가 발생한다.")
    void test03() {
        // given
        LoginMemberAuthCommand command = new LoginMemberAuthCommand("TEST_MEMBER", "INCORRECT_PASSWORD");

        // when & then
        assertThrows(LoginFailException.class, () ->
                loginMemberAuthService.login(command)
        );
    }

}
