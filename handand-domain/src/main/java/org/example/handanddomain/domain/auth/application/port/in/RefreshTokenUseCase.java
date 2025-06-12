package org.example.handanddomain.domain.auth.application.port.in;

import org.example.handanddomain.domain.auth.application.port.in.dto.LoginResult;

public interface RefreshTokenUseCase {

    LoginResult refreshToken(String refreshToken);

}
