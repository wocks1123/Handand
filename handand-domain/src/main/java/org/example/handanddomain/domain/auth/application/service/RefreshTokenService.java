package org.example.handanddomain.domain.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.common.exception.DomainIllegalArgumentException;
import org.example.handanddomain.domain.auth.application.port.in.RefreshTokenUseCase;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginResult;
import org.example.handanddomain.domain.auth.application.port.out.FindRefreshTokenPort;
import org.example.handanddomain.domain.auth.application.port.out.SaveRefreshTokenPort;
import org.example.handanddomain.domain.auth.application.port.out.TokenPort;
import org.example.handanddomain.domain.auth.domain.RefreshToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenUseCase {

    private final FindRefreshTokenPort findRefreshTokenPort;
    private final SaveRefreshTokenPort saveRefreshTokenPort;
    private final TokenPort tokenPort;


    @Override
    public LoginResult refreshToken(String refreshToken) {

        if (!tokenPort.isValidateToken(refreshToken)) {
            throw new DomainIllegalArgumentException("invalid refreshToken");
        }

        var token = findRefreshTokenPort.findByToken(refreshToken);

        var username = tokenPort.extractUsername(refreshToken);

        token.revoke();
        String accessToken = tokenPort.generateAccessToken(username);
        String refreshTokenStr = tokenPort.generateRefreshToken(accessToken);
        RefreshToken newToken = saveRefreshTokenPort.save(new RefreshToken(refreshTokenStr, token.getMemberId()));
        saveRefreshTokenPort.save(newToken);
        return new LoginResult(accessToken, refreshTokenStr, token.getMemberId());
    }
}
