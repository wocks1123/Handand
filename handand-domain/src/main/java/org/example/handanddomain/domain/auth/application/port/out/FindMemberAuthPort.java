package org.example.handanddomain.domain.auth.application.port.out;

import org.example.handanddomain.domain.auth.domain.MemberAuth;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface FindMemberAuthPort {

    @NotNull Optional<MemberAuth> findByUsername(@NotNull String username);

}
