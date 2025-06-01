package org.example.handanddomain.domain.member.application.service;

import org.example.handanddomain.common.exception.DomainIllegalArgumentException;
import org.example.handanddomain.domain.member.application.exception.MemberNotFoundException;
import org.example.handanddomain.domain.member.application.port.in.dto.ModifyMemberCommand;
import org.example.handanddomain.domain.member.application.port.in.dto.RegisterMemberCommand;
import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.member.infra.MemberFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    private MemberService memberService;
    private MemberFakeRepository memberFakeRepository;

    @BeforeEach
    void setUp() {
        memberFakeRepository = new MemberFakeRepository();
        memberService = new MemberService(memberFakeRepository, memberFakeRepository);
    }

    @Test
    @DisplayName("회원을 등록한다.")
    void test01() {
        // given
        var command = new RegisterMemberCommand("TEST_NAME", "image://TEST_IMAGE_URL");

        // when
        var memberId = memberService.registerMember(command);

        // then
        var member = memberFakeRepository.findById(memberId).orElse(null);

        assertThat(member).isNotNull();
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(memberId),
                () -> assertThat(member.getName()).isEqualTo(command.name()),
                () -> assertThat(member.getProfileImageUrl()).isEqualTo(command.profileImageUrl())
        );
    }

    @Test
    @DisplayName("회원 등록 시 프로필 사진을 지정하지 않으면 기본 프로필이 설정된다.")
    void test02() {
        // given
        var command = new RegisterMemberCommand("TEST_NAME", null);

        // when
        var memberId = memberService.registerMember(command);

        // then
        var member = memberFakeRepository.findById(memberId).orElse(null);

        assertThat(member).isNotNull();
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(memberId),
                () -> assertThat(member.getName()).isEqualTo(command.name()),
                () -> assertThat(member.getProfileImageUrl()).isEqualTo(Member.DEFAULT_PROFILE_IMAGE_URL)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원 등록 시 이름이 비어있으면 예외가 발생한다.")
    void test03(String nullableEmptyName) {
        // given
        var command = new RegisterMemberCommand(nullableEmptyName, "image://TEST_IMAGE_URL");

        // when & then
        assertThrows(DomainIllegalArgumentException.class, () -> {
            memberService.registerMember(command);
        });
    }

    @Test
    @DisplayName("등록된 회원 정보를 조회한다.")
    void test04() {
        // given
        var command = new RegisterMemberCommand("TEST_NAME", "image://TEST_IMAGE_URL");
        var memberId = memberService.registerMember(command);

        // when
        var member = memberService.getMember(memberId);

        // then
        assertThat(member).isNotNull();
        assertAll(
                () -> assertThat(member.getId()).isEqualTo(memberId),
                () -> assertThat(member.getName()).isEqualTo(command.name()),
                () -> assertThat(member.getProfileImageUrl()).isEqualTo(command.profileImageUrl())
        );
    }

    @Test
    @DisplayName("등록되지 않은 회원 정보를 조회하면 예외가 발생한다.")
    void test05() {
        // given
        var nonExistentMemberId = 999L;

        // when & then
        assertThrows(MemberNotFoundException.class, () -> {
            memberService.getMember(nonExistentMemberId);
        });
    }

    @Test
    @DisplayName("회원 이름으로 등록된 회원 정보를 조회한다.")
    void test06() {
        // given
        var command = new RegisterMemberCommand("TEST_NAME", "image://TEST_IMAGE_URL");
        memberService.registerMember(command);

        // when
        var member = memberService.getMemberByName(command.name());

        // then
        assertThat(member).isNotNull();
        assertAll(
                () -> assertThat(member.getName()).isEqualTo(command.name()),
                () -> assertThat(member.getProfileImageUrl()).isEqualTo(command.profileImageUrl())
        );
    }

    @Test
    @DisplayName("등록되지 않은 회원 이름으로 조회하면 예외가 발생한다.")
    void test07() {
        // given
        var nonExistentName = "NON_EXISTENT_NAME";

        // when & then
        assertThrows(MemberNotFoundException.class, () -> {
            memberService.getMemberByName(nonExistentName);
        });
    }

    @Test
    @DisplayName("회원 정보를 수정한다.")
    void test08() {
        // given
        var command = new RegisterMemberCommand("TEST_NAME", "image://TEST_IMAGE_URL");
        var memberId = memberService.registerMember(command);
        var modifyCommand = new ModifyMemberCommand(memberId, "image://MODIFIED_TEST_IMAGE_URL");

        // when
        memberService.modifyMember(modifyCommand);

        // then
        var modifiedMember = memberService.getMember(memberId);
        assertThat(modifiedMember).isNotNull();
        assertAll(
                () -> assertThat(modifiedMember.getId()).isEqualTo(memberId),
                () -> assertThat(modifiedMember.getProfileImageUrl()).isEqualTo(modifyCommand.profileImageUrl())
        );
    }

    @Test
    @DisplayName("회원 정보 수정 시 프로필 사진을 설정하지 않으면 기본 프로필이 설정된다.")
    void test09() {
        // given
        var command = new RegisterMemberCommand("TEST_NAME", "image://TEST_IMAGE_URL");
        var memberId = memberService.registerMember(command);
        var modifyCommand = new ModifyMemberCommand(memberId, null);

        // when
        memberService.modifyMember(modifyCommand);

        // then
        var modifiedMember = memberService.getMember(memberId);
        assertThat(modifiedMember).isNotNull();
        assertAll(
                () -> assertThat(modifiedMember.getId()).isEqualTo(memberId),
                () -> assertThat(modifiedMember.getProfileImageUrl()).isEqualTo(Member.DEFAULT_PROFILE_IMAGE_URL)
        );
    }

    @Test
    @DisplayName("회원 정보 수정 시 등록되지 않은 회원을 지정하면 예외가 발생한다.")
    void test10() {
        // given
        var nonExistentMemberId = 999L;
        var modifyCommand = new ModifyMemberCommand(nonExistentMemberId, "image://MODIFIED_TEST_IMAGE_URL");

        // when & then
        assertThrows(MemberNotFoundException.class, () -> {
            memberService.modifyMember(modifyCommand);
        });
    }

}
