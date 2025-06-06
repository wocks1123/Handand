package org.example.handanddomain.domain.auth.infra.jpa;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberAuthJpaRepository extends JpaRepository<MemberAuthJpaEntity, Long> {

    Optional<MemberAuthJpaEntity> findByUsername(@NotNull String username);

}
