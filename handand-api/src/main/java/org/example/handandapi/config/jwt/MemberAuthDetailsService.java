package org.example.handandapi.config.jwt;

import lombok.RequiredArgsConstructor;
import org.example.handanddomain.domain.auth.application.port.out.FindMemberAuthPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAuthDetailsService implements UserDetailsService {

    private final FindMemberAuthPort findMemberAuthPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findMemberAuthPort.findByUsername(username)
                .map(MemberAuthDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다: " + username));
    }

}
