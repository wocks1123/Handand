package org.example.handanddomain.domain.member.application.port.in;

import org.example.handanddomain.domain.member.application.port.in.dto.RegisterMemberCommand;
import org.jetbrains.annotations.NotNull;

public interface RegisterMemberUseCase {

    @NotNull Long registerMember(@NotNull RegisterMemberCommand command);

}
