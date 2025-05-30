package org.example.handanddomain.domain.member.infra.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.handanddomain.domain.member.domain.Member;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String profileImageUrl;

    public MemberJpaEntity(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.profileImageUrl = member.getProfileImageUrl();
    }

    public Member toDomain() {
        return new Member(id, name, profileImageUrl);
    }

}
