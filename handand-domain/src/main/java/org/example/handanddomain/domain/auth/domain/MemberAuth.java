package org.example.handanddomain.domain.auth.domain;

import lombok.Getter;

@Getter
public class MemberAuth {

    private final Long id;
    private final Long memberId;
    private final String username;
    private String password;
    private final LoginType loginType;
    private final String externalId;

    public MemberAuth(Long id, Long memberId, String username, String password, LoginType loginType, String externalId) {
        this.id = id;
        this.memberId = memberId;
        this.username = username;
        this.password = password;
        this.loginType = loginType;
        this.externalId = externalId;
    }

    static MemberAuth withLocal(Long memberId,
                                String username,
                                String password) {
        return new MemberAuth(null, memberId, username, password, LoginType.Local, null);
    }

    public void changePassword(String newEncodedPassword) {
        // TODO: 소셜 로그인 미구현
        /*if (this.loginType != LoginType.Local) {
            throw new DomainIllegalArgumentException("소셜 로그인 사용자는 비밀번호를 변경할 수 없습니다");
        }*/
        this.password = newEncodedPassword;
    }

}
