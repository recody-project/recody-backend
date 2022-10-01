package com.recody.recodybackend.users.features.login.admin;

import com.recody.recodybackend.commonbootutils.jwt.CreateAccessToken;
import com.recody.recodybackend.commonbootutils.jwt.DefaultSuperJwtManager;
import com.recody.recodybackend.users.data.RecodyUser;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.data.Role;
import com.recody.recodybackend.users.features.login.SocialProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"local", "test"})
class LocalAdminUserRegisterer {
    
    private final RecodyUserRepository recodyUserRepository;
    private final DefaultSuperJwtManager superJwtManager;
    
    @Value("${users.admin.username}")
    private String username;
    
    @Value("${users.admin.password}")
    private String password;
    
    @Value("${users.admin.email}")
    private String email;
    
    private Long userId;
    
    @PostConstruct
    void register(){
        RecodyUser user = RecodyUser
                .builder()
                .role(Role.ROLE_ADMIN)
                .username(username)
                .password(password)
                .socialType(SocialProvider.NONE)
                .email(email)
                .build();
        RecodyUser savedUser = recodyUserRepository.save(user);
        this.userId = savedUser.getUserId();
        log.info("기본 어드민 유저 등록 성공");
    }
    
    String createToken(){
        CreateAccessToken command = CreateAccessToken.builder().email(email).userId(userId).build();
        String accessToken = superJwtManager.createAccessToken(command);
        return accessToken;
    }
}
