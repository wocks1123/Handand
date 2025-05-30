package org.example.handanddomain.domain.member.application.port.out;

import org.example.handanddomain.domain.member.domain.Member;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface LoadMemberPort {

    Optional<Member> findById(@NotNull Long id);

    Optional<Member> findByName(@NotNull String name);

}
