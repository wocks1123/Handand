package org.example.handanddomain.domain.member.infra;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.common.exception.DomainIllegalArgumentException;
import org.example.handanddomain.domain.auth.application.port.out.FindRefreshTokenPort;
import org.example.handanddomain.domain.auth.application.port.out.SaveRefreshTokenPort;
import org.example.handanddomain.domain.auth.domain.RefreshToken;
import org.example.handanddomain.domain.member.infra.jpa.RefreshTokenJpaEntity;
import org.example.handanddomain.domain.member.infra.jpa.RefreshTokenJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements SaveRefreshTokenPort, FindRefreshTokenPort {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;


    @Override
    public @NotNull RefreshToken save(@NotNull RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(RefreshTokenJpaEntity.from(refreshToken))
                .toDomain();
    }

    @Override
    public @NotNull RefreshToken findByToken(@NotNull String token) {
        return refreshTokenJpaRepository.findByToken(token)
                .orElseThrow(() -> new DomainIllegalArgumentException("refreshToken not found"));
    }

}
