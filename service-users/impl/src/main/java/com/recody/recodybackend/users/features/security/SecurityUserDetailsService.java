package com.recody.recodybackend.users.features.security;

import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SecurityUserDetailsService implements UserDetailsService {

    private final RecodyUserRepository recodyUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RecodyUserEntity userInfo = recodyUserRepository.findByEmail( username )
                                                        .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        return new SecurityUser(userInfo);
    }
}
