package org.example.handanddomain.domain.member.application.port.in;

import org.example.handanddomain.domain.member.application.port.in.dto.ModifyMemberCommand;
import org.jetbrains.annotations.NotNull;

public interface ModifyMemberUseCase {

    void modifyMember(@NotNull ModifyMemberCommand command);

}
