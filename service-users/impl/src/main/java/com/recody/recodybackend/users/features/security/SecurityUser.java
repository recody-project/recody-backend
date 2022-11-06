package com.recody.recodybackend.users.features.security;

import com.recody.recodybackend.users.data.RecodyUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

class SecurityUser extends User {
    
    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    
    public SecurityUser(RecodyUserEntity recodyUserEntity) {
        super(
                recodyUserEntity.getEmail(),
                "",
                AuthorityUtils.createAuthorityList( recodyUserEntity.getRole().toString() ) );
    }
}
