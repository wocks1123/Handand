package org.example.handanddomain.domain.member.application.port.in;

import org.example.handanddomain.domain.member.application.exception.MemberNotFoundException;
import org.example.handanddomain.domain.member.domain.Member;
import org.jetbrains.annotations.NotNull;

public interface GetMemberUseCase {

    @NotNull Member getMember(@NotNull Long memberId) throws MemberNotFoundException;

    @NotNull Member getMemberByName(@NotNull String name) throws MemberNotFoundException;

}
