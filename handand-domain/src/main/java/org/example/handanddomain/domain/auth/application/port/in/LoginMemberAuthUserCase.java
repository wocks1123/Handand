package org.example.handanddomain.domain.auth.application.port.in;

import org.example.handanddomain.domain.auth.application.exception.LoginFailException;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginMemberAuthCommand;
import org.jetbrains.annotations.NotNull;

public interface LoginMemberAuthUserCase {

    void login(@NotNull LoginMemberAuthCommand command) throws LoginFailException;

}
