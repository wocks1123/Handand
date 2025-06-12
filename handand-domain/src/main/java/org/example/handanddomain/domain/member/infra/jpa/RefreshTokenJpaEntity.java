package org.example.handanddomain.domain.member.infra.jpa;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.handanddomain.domain.auth.domain.RefreshToken;

@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class RefreshTokenJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "revoked", nullable = false)
    private boolean revoked = false;

    public RefreshTokenJpaEntity(String token, Long memberId, boolean revoked) {
        this.token = token;
        this.memberId = memberId;
        this.revoked = revoked;
    }

    public static RefreshTokenJpaEntity from(String token, Long memberId, boolean revoked) {
        return new RefreshTokenJpaEntity(token, memberId, revoked);
    }

    public static RefreshTokenJpaEntity from(RefreshToken refreshToken) {
        return new RefreshTokenJpaEntity(refreshToken.getToken(), refreshToken.getMemberId(), refreshToken.isRevoked());
    }

    public RefreshToken toDomain() {
        return new RefreshToken(token, memberId, revoked);
    }

}
