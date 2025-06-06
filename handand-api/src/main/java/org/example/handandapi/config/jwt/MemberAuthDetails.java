package org.example.handandapi.config.jwt;

import org.example.handanddomain.domain.auth.domain.MemberAuth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MemberAuthDetails implements UserDetails {

    private final MemberAuth memberAuth;

    public MemberAuthDetails(MemberAuth memberAuth) {
        this.memberAuth = memberAuth;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return memberAuth.getPassword();
    }

    @Override
    public String getUsername() {
        return memberAuth.getUsername();
    }

}
