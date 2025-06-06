package org.example.handanddomain.domain.auth.application.service;

import org.example.handanddomain.domain.auth.application.port.in.dto.RegisterMemberWithAuthCommand;
import org.example.handanddomain.domain.auth.domain.MemberAuthDomainService;
import org.example.handanddomain.domain.auth.infra.FakeMemberAuthRepository;
import org.example.handanddomain.domain.auth.infra.FakePasswordEncoder;
import org.example.handanddomain.domain.member.application.port.in.RegisterMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.dto.RegisterMemberCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterMemberWithAuthServiceTest {

    private RegisterMemberWithAuthService registerMemberWithAuthService;


    @BeforeEach
    void setup() {
        RegisterMemberUseCase registerMemberUseCase = new RegisterMemberUseCase() {
            @Override
            public Long registerMember(RegisterMemberCommand command) {
                return 1L;
            }
        };

        MemberAuthDomainService memberAuthDomainService = new MemberAuthDomainService(new FakePasswordEncoder());
        registerMemberWithAuthService = new RegisterMemberWithAuthService(
                registerMemberUseCase,
                memberAuthDomainService,
                new FakeMemberAuthRepository()
        );
    }


    @Test
    void registerMemberWithAuth() {
        // given
        RegisterMemberWithAuthCommand command = new RegisterMemberWithAuthCommand(
                "TEST_USER",
                "TEST_PASSWORD",
                "TEST_NAME",
                null
        );

        // when
        Long memberId = registerMemberWithAuthService.registerMemberWithAuth(command);

        // then
        assertThat(memberId).isEqualTo(1L);
    }


}
