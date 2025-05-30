package org.example.handanddomain.domain.member.application.port.out;

import org.example.handanddomain.domain.member.domain.Member;
import org.jetbrains.annotations.NotNull;

public interface SaveMemberPort {

    @NotNull Member save(@NotNull Member member);

}
