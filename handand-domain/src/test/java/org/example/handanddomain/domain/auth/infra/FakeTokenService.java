package org.example.handanddomain.domain.auth.infra;

import org.example.handanddomain.domain.auth.application.port.out.TokenPort;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FakeTokenService implements TokenPort {

    private static final String TOKEN_PREFIX = "FAKE_TOKEN_";
    private static final String REFRESH_TOKEN_PREFIX = "FAKE_REFRESH_TOKEN_";

    @Override
    public @NotNull String generateAccessToken(@NotNull String username) {
        return TOKEN_PREFIX + username;
    }

    @Override
    public @NotNull String generateRefreshToken(@NotNull String existingToken) {
        return REFRESH_TOKEN_PREFIX + existingToken.replace(TOKEN_PREFIX, "");
    }

    @Override
    public boolean isValidateToken(@Nullable String token) {
        return token != null && (token.startsWith(TOKEN_PREFIX) || token.startsWith(REFRESH_TOKEN_PREFIX));
    }

    @Override
    public @NotNull String extractUsername(@NotNull String token) {
        if (token.startsWith(REFRESH_TOKEN_PREFIX)) {
            token = token.replace(REFRESH_TOKEN_PREFIX, "");
        }
        return token.replace(TOKEN_PREFIX, "");
    }

}
