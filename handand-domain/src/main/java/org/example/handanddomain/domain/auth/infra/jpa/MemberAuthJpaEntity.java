package org.example.handanddomain.domain.auth.infra.jpa;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.handanddomain.domain.auth.domain.LoginType;
import org.example.handanddomain.domain.auth.domain.MemberAuth;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "member_auth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuthJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @Column(name = "external_id")
    private String externalId;

    public static MemberAuthJpaEntity from(@NotNull MemberAuth memberAuth) {
        MemberAuthJpaEntity entity = new MemberAuthJpaEntity();
        entity.id = memberAuth.getId();
        entity.memberId = memberAuth.getMemberId();
        entity.username = memberAuth.getUsername();
        entity.password = memberAuth.getPassword();
        entity.loginType = memberAuth.getLoginType();
        entity.externalId = memberAuth.getExternalId();
        return entity;
    }

    public MemberAuth toDomain() {
        return new MemberAuth(
                id,
                memberId,
                username,
                password,
                loginType,
                externalId
        );
    }
}
