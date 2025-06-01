package org.example.handanddomain.domain.member.application.port.in;

import org.example.handanddomain.domain.member.domain.Member;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MemberUseCaseFactory {

    public static GetMemberUseCase createFakeGetMemberUseCase(Member... givenMember) {
        return new GetMemberUseCase() {

            private final List<Member> members = List.of(givenMember);

            @Override
            public @NotNull Member getMember(@NotNull Long memberId) {
                return members.stream()
                        .filter(member -> member.getId().equals(memberId))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("FakeGetMemberUseCase Exception"));
            }

            @Override
            public @NotNull Member getMemberByName(@NotNull String name) {
                return members.stream()
                        .filter(member -> member.getName().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("FakeGetMemberUseCase Exception"));
            }
        };
    }

}
