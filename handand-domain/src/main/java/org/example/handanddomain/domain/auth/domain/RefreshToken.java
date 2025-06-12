package org.example.handanddomain.domain.auth.domain;

import lombok.Getter;

@Getter
public class RefreshToken {

    private final String token;
    private final Long memberId;
    private boolean revoked;


    public RefreshToken(String token, Long memberId) {
        this.token = token;
        this.memberId = memberId;
        this.revoked = false;
    }

    public RefreshToken(String token, Long memberId, boolean revoked) {
        this.token = token;
        this.memberId = memberId;
        this.revoked = revoked;
    }

    public void revoke() {
        this.revoked = true;
    }

}
