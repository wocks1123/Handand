package org.example.handandapi.ui.auth;

import lombok.RequiredArgsConstructor;
import org.example.handandapi.ui.auth.dto.LoginRequest;
import org.example.handandapi.ui.auth.dto.RegisterMemberWithAuthRequest;
import org.example.handanddomain.domain.auth.application.port.in.LoginMemberAuthUserCase;
import org.example.handanddomain.domain.auth.application.port.in.RegisterMemberWithAuthUseCase;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginMemberAuthCommand;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginResult;
import org.example.handanddomain.domain.auth.application.port.in.dto.RegisterMemberWithAuthCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
class AuthRestController {

    private final RegisterMemberWithAuthUseCase registerMemberWithAuthUseCase;
    private final LoginMemberAuthUserCase loginMemberAuthUserCase;


    @PostMapping("/register")
    Long registerMemberWithAuth(@RequestBody RegisterMemberWithAuthRequest request) {
        return registerMemberWithAuthUseCase.registerMemberWithAuth(
                new RegisterMemberWithAuthCommand(
                        request.username(),
                        request.password(),
                        request.name(),
                        request.profileImageUrl()
                )
        );
    }

    @PostMapping("/login")
    LoginResult login(@RequestBody LoginRequest request) {
        return loginMemberAuthUserCase.login(
                new LoginMemberAuthCommand(
                        request.username(),
                        request.password()
                )
        );
    }

}
