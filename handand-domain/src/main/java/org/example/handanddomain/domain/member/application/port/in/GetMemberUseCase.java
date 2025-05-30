package org.example.handanddomain.domain.member.application.port.in;

import org.example.handanddomain.domain.member.domain.Member;
import org.jetbrains.annotations.NotNull;

public interface GetMemberUseCase {

    @NotNull Member getMember(@NotNull Long memberId);

    @NotNull Member getMemberByName(@NotNull String name);

}
