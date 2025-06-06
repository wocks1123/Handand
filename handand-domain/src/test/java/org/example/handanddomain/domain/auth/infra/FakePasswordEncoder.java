package org.example.handanddomain.domain.auth.infra;

import org.example.handanddomain.domain.auth.domain.PasswordEncoderPort;
import org.jetbrains.annotations.NotNull;

public class FakePasswordEncoder implements PasswordEncoderPort {

    @Override
    public @NotNull String encode(@NotNull String rawPassword) {
        return rawPassword + "_encoded";
    }

    @Override
    public boolean matches(@NotNull String rawPassword, @NotNull String encodedPassword) {
        return encodedPassword.endsWith(rawPassword + "_encoded");
    }

}
