package org.example.handandapi.ui.member;

import lombok.RequiredArgsConstructor;
import org.example.handandapi.ui.member.dto.ModifyMemberRequest;
import org.example.handandapi.ui.member.dto.RegisterMemberRequest;
import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.ModifyMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.RegisterMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.dto.ModifyMemberCommand;
import org.example.handanddomain.domain.member.application.port.in.dto.RegisterMemberCommand;
import org.example.handanddomain.domain.member.domain.Member;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
class MemberRestController {

    private final RegisterMemberUseCase registerMemberUseCase;
    private final ModifyMemberUseCase modifyMemberUseCase;
    private final GetMemberUseCase getMemberUseCase;

    @PostMapping
    Long registerMember(@RequestBody RegisterMemberRequest request) {
        return registerMemberUseCase.registerMember(
                new RegisterMemberCommand(
                        request.name(),
                        request.profileImageUrl()
                )
        );
    }

    @PutMapping("/{memberId}")
    void modifyMember(@PathVariable Long memberId,
                      @RequestBody ModifyMemberRequest request) {
        modifyMemberUseCase.modifyMember(
                new ModifyMemberCommand(
                        memberId,
                        request.profileImageUrl()
                )
        );
    }

    @GetMapping("/{memberId}")
    Member getMember(@PathVariable Long memberId) {
        return getMemberUseCase.getMember(memberId);
    }

}
