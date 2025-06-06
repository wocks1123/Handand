package org.example.handanddomain.domain.auth.domain;

import org.jetbrains.annotations.NotNull;

public interface PasswordEncoderPort {

    @NotNull String encode(@NotNull String rawPassword);

    boolean matches(@NotNull String rawPassword, @NotNull String encodedPassword);

}
