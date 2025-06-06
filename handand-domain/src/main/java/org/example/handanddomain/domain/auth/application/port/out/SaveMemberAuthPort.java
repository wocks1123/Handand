package org.example.handanddomain.domain.auth.application.port.out;

import org.example.handanddomain.domain.auth.domain.MemberAuth;
import org.jetbrains.annotations.NotNull;

public interface SaveMemberAuthPort {

    @NotNull MemberAuth save(@NotNull MemberAuth memberAuth);

}
