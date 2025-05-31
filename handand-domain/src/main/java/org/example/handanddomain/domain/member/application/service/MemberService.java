package org.example.handanddomain.domain.member.application.service;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.ModifyMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.RegisterMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.dto.ModifyMemberCommand;
import org.example.handanddomain.domain.member.application.port.in.dto.RegisterMemberCommand;
import org.example.handanddomain.domain.member.application.port.out.LoadMemberPort;
import org.example.handanddomain.domain.member.application.port.out.SaveMemberPort;
import org.example.handanddomain.domain.member.domain.Member;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class MemberService implements RegisterMemberUseCase, ModifyMemberUseCase, GetMemberUseCase {

    private final SaveMemberPort saveMemberPort;
    private final LoadMemberPort loadMemberPort;


    @Override
    public @NotNull Long registerMember(@NotNull RegisterMemberCommand command) {
        Member member = new Member(null, command.name(), command.profileImageUrl());
        return saveMemberPort.save(member).getId();
    }

    @Override
    public @NotNull Member getMember(@NotNull Long memberId) {
        return loadMemberPort.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
    }

    @Override
    public @NotNull Member getMemberByName(@NotNull String name) {
        return loadMemberPort.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. name:" + name));
    }

    @Override
    public void modifyMember(@NotNull ModifyMemberCommand command) {
        Member member = loadMemberPort.findById(command.memberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + command.memberId()));

        member.modifyProfileImageUrl(command.profileImageUrl());

        saveMemberPort.save(member);
    }

}
