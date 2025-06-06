package org.example.handanddomain.domain.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.auth.application.port.in.RegisterMemberWithAuthUseCase;
import org.example.handanddomain.domain.auth.application.port.in.dto.RegisterMemberWithAuthCommand;
import org.example.handanddomain.domain.auth.application.port.out.SaveMemberAuthPort;
import org.example.handanddomain.domain.auth.domain.MemberAuth;
import org.example.handanddomain.domain.auth.domain.MemberAuthDomainService;
import org.example.handanddomain.domain.member.application.port.in.RegisterMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.dto.RegisterMemberCommand;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
class RegisterMemberWithAuthService implements RegisterMemberWithAuthUseCase {

    private final RegisterMemberUseCase registerMemberUseCase;
    private final MemberAuthDomainService memberAuthDomainService;
    private final SaveMemberAuthPort saveMemberAuthPort;


    @Override
    public @NotNull Long registerMemberWithAuth(@NotNull RegisterMemberWithAuthCommand command) {
        Long memberId = registerMemberUseCase.registerMember(
                new RegisterMemberCommand(
                        command.name(),
                        command.profileImageUrl()
                )
        );

        MemberAuth memberAuth = memberAuthDomainService.createMemberAuth(memberId, command.username(), command.password());
        MemberAuth savedMemberAuth = saveMemberAuthPort.save(memberAuth);

        return savedMemberAuth.getMemberId();
    }

}
