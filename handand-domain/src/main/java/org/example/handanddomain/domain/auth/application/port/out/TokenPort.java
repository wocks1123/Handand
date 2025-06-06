package org.example.handanddomain.domain.auth.application.port.out;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TokenPort {

    @NotNull String generateAccessToken(@NotNull String username);

    @NotNull String generateRefreshToken(@NotNull String existingToken);

    boolean isValidateToken(@Nullable String token);

    @NotNull String extractUsername(@NotNull String token);

}
