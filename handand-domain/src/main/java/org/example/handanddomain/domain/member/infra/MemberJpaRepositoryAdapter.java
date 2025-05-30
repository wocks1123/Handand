package org.example.handanddomain.domain.member.infra;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.member.application.port.out.LoadMemberPort;
import org.example.handanddomain.domain.member.application.port.out.SaveMemberPort;
import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.member.infra.jpa.MemberJpaEntity;
import org.example.handanddomain.domain.member.infra.jpa.MemberJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryAdapter implements SaveMemberPort, LoadMemberPort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findById(@NotNull Long id) {
        return memberJpaRepository.findById(id)
                .map(MemberJpaEntity::toDomain);
    }

    @Override
    public Optional<Member> findByName(@NotNull String name) {
        return memberJpaRepository.findByName(name)
                .map(MemberJpaEntity::toDomain);
    }

    @Override
    public @NotNull Member save(@NotNull Member member) {
        return memberJpaRepository.save(new MemberJpaEntity(member))
                .toDomain();
    }

}
