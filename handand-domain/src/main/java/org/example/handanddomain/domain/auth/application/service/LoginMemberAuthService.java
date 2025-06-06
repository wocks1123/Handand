package org.example.handanddomain.domain.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.auth.application.exception.LoginFailException;
import org.example.handanddomain.domain.auth.application.port.in.LoginMemberAuthUserCase;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginMemberAuthCommand;
import org.example.handanddomain.domain.auth.application.port.out.FindMemberAuthPort;
import org.example.handanddomain.domain.auth.domain.MemberAuth;
import org.example.handanddomain.domain.auth.domain.MemberAuthDomainService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoginMemberAuthService implements LoginMemberAuthUserCase {

    private final FindMemberAuthPort findMemberAuthPort;
    private final MemberAuthDomainService memberAuthDomainService;


    @Override
    public void login(@NotNull LoginMemberAuthCommand command) throws LoginFailException {
        MemberAuth memberAuth = findMemberAuthPort.findByUsername(command.username())
                .orElseThrow(LoginFailException::new);

        if (!memberAuthDomainService.checkPassword(memberAuth, command.password())) {
            throw new LoginFailException();
        }
    }

}
