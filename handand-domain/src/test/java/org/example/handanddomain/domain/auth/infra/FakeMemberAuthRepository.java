package org.example.handanddomain.domain.auth.infra;

import org.example.handanddomain.domain.auth.application.port.out.FindMemberAuthPort;
import org.example.handanddomain.domain.auth.application.port.out.SaveMemberAuthPort;
import org.example.handanddomain.domain.auth.domain.MemberAuth;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeMemberAuthRepository implements SaveMemberAuthPort, FindMemberAuthPort {

    private final Map<Long, MemberAuth> memberAuths = new ConcurrentHashMap<>();
    private final Map<String, MemberAuth> memberAuthsByUsername = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);


    @Override
    public @NotNull MemberAuth save(@NotNull MemberAuth memberAuth) {
        if (memberAuth.getId() == null) {
            memberAuth = new MemberAuth(
                    idGenerator.getAndIncrement(),
                    memberAuth.getMemberId(),
                    memberAuth.getUsername(),
                    memberAuth.getPassword(),
                    memberAuth.getLoginType(),
                    memberAuth.getExternalId()
            );
        }
        memberAuths.put(memberAuth.getId(), memberAuth);
        memberAuthsByUsername.put(memberAuth.getUsername(), memberAuth);
        return memberAuth;
    }

    @Override
    public @NotNull Optional<MemberAuth> findByUsername(@NotNull String username) {
        return memberAuthsByUsername.get(username) != null
                ? Optional.of(memberAuthsByUsername.get(username))
                : Optional.empty();
    }

}
