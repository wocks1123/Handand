package org.example.handanddomain.domain.auth.application.port.in;

import org.example.handanddomain.domain.auth.application.exception.LoginFailException;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginMemberAuthCommand;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginResult;
import org.jetbrains.annotations.NotNull;

public interface LoginMemberAuthUserCase {

    @NotNull LoginResult login(@NotNull LoginMemberAuthCommand command) throws LoginFailException;

}
