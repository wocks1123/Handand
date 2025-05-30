package org.example.handanddomain.domain.member.application.service;

import org.example.handanddomain.domain.member.application.port.in.dto.RegisterMemberCommand;
import org.example.handanddomain.domain.member.infra.MemberFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberServiceTest {

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        var repository = new MemberFakeRepository();
        memberService = new MemberService(repository, repository);
    }

    @Test
    @DisplayName("회원을 등록한다.")
    void test01() {
        // given
        var command = new RegisterMemberCommand("TEST_NAME", "image://TEST_IMAGE_URL");

        // when
        var memberId = memberService.registerMember(command);

        // then
        var member = memberService.getMember(memberId);

        assertThat(member).isNotNull();
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(memberId),
                () -> assertThat(member.getName()).isEqualTo(command.name()),
                () -> assertThat(member.getProfileImageUrl()).isEqualTo(command.profileImageUrl())
        );
    }

}
