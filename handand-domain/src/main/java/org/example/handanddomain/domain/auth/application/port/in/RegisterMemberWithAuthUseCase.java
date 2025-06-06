package org.example.handanddomain.domain.auth.application.port.in;

import org.example.handanddomain.domain.auth.application.port.in.dto.RegisterMemberWithAuthCommand;
import org.jetbrains.annotations.NotNull;

public interface RegisterMemberWithAuthUseCase {

    @NotNull Long registerMemberWithAuth(@NotNull RegisterMemberWithAuthCommand command);

}
