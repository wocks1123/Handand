package org.example.handanddomain.domain.auth.infra;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.auth.application.port.out.FindMemberAuthPort;
import org.example.handanddomain.domain.auth.application.port.out.SaveMemberAuthPort;
import org.example.handanddomain.domain.auth.domain.MemberAuth;
import org.example.handanddomain.domain.auth.infra.jpa.MemberAuthJpaEntity;
import org.example.handanddomain.domain.auth.infra.jpa.MemberAuthJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberAuthRepositoryAdapter implements SaveMemberAuthPort, FindMemberAuthPort {

    private final MemberAuthJpaRepository memberAuthJpaRepository;


    @Override
    public @NotNull MemberAuth save(@NotNull MemberAuth memberAuth) {
        return memberAuthJpaRepository.save(MemberAuthJpaEntity.from(memberAuth))
                .toDomain();
    }

    @Override
    public @NotNull Optional<MemberAuth> findByUsername(@NotNull String username) {
        return memberAuthJpaRepository.findByUsername(username)
                .map(MemberAuthJpaEntity::toDomain);
    }

}
