package org.example.handanddomain.domain.member.infra.jpa;

import org.example.handanddomain.domain.auth.domain.RefreshToken;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {

    Optional<RefreshToken> findByToken(String token);

}
