package com.recody.recodybackend.users.features.login.admin;

import com.recody.recodybackend.users.data.RecodyUser;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.data.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
class AdminUserRegister {
    
    private final RecodyUserRepository recodyUserRepository;
    
    @Value("${users.admin.username}")
    private String username;
    
    @Value("${users.admin.password}")
    private String password;
    
    @Value("${users.admin.email}")
    private String email;
    
    @PostConstruct
    void register(){
        RecodyUser user = RecodyUser
                .builder()
                .role(Role.ROLE_ADMIN)
                .username(username)
                .password(password)
                .email(email)
                .build();
        recodyUserRepository.save(user);
        log.info("기본 어드민 유저 등록 성공");
    }
}
