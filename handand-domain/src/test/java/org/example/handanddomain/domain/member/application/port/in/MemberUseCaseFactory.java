package org.example.handanddomain.domain.member.application.port.in;

import org.example.handanddomain.domain.member.domain.Member;

import java.util.List;

public class MemberUseCaseFactory {

    public static GetMemberUseCase createFakeGetMemberUseCase(Member... givenMember) {
        return new GetMemberUseCase() {

            private final List<Member> members = List.of(givenMember);

            @Override
            public Member getMember(Long memberId) {
                return members.stream()
                        .filter(member -> member.getId().equals(memberId))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("FakeGetMemberUseCase Exception"));
            }

            @Override
            public Member getMemberByName(String name) {
                return members.stream()
                        .filter(member -> member.getName().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("FakeGetMemberUseCase Exception"));
            }
        };
    }

}
