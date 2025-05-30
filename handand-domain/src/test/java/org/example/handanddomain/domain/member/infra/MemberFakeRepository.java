package org.example.handanddomain.domain.member.infra;

import org.example.handanddomain.domain.member.application.port.out.LoadMemberPort;
import org.example.handanddomain.domain.member.application.port.out.SaveMemberPort;
import org.example.handanddomain.domain.member.domain.Member;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemberFakeRepository implements SaveMemberPort, LoadMemberPort {

    private final Map<Long, Member> members = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public @NotNull Member save(@NotNull Member member) {
        if (member.getId() == null) {
            member = new Member(idGenerator.getAndIncrement(), member.getName(), member.getProfileImageUrl());
        }
        members.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(@NotNull Long id) {
        return Optional.ofNullable(members.get(id));
    }

    @Override
    public Optional<Member> findByName(@NotNull String name) {
        return members.values().stream()
                .filter(member -> member.getName().equals(name))
                .findFirst();
    }

}
