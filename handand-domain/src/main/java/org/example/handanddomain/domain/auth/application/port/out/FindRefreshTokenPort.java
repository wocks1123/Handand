package org.example.handanddomain.domain.auth.application.port.out;

import org.example.handanddomain.domain.auth.domain.RefreshToken;
import org.jetbrains.annotations.NotNull;

public interface FindRefreshTokenPort {

    @NotNull RefreshToken findByToken(@NotNull String token);

}
