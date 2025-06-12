package org.example.handanddomain.domain.auth.application.port.out;

import org.example.handanddomain.domain.auth.domain.RefreshToken;
import org.jetbrains.annotations.NotNull;

public interface SaveRefreshTokenPort {

    @NotNull RefreshToken save(@NotNull RefreshToken refreshToken);

}
