package org.example.handanddomain.domain.auth.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAuthDomainService {

    private final PasswordEncoderPort passwordEncoderPort;


    public MemberAuth createMemberAuth(Long memberId, String username, String rawPassword) {
        String encodedPassword = passwordEncoderPort.encode(rawPassword);
        return MemberAuth.withLocal(memberId, username, encodedPassword);
    }

    public void changePassword(MemberAuth memberAuth, String newRawPassword) {
        String newEncodedPassword = passwordEncoderPort.encode(newRawPassword);
        memberAuth.changePassword(newEncodedPassword);
    }

    public boolean checkPassword(MemberAuth memberAuth, String rawPassword) {
        return passwordEncoderPort.matches(rawPassword, memberAuth.getPassword());
    }

}
