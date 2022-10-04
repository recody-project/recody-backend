package com.recody.recodybackend.users.features.login.admin;

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
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"local", "test"})
class AdminRegistrar {
    
    private final DefaultSuperJwtManager superJwtManager;
    
    private final RecodyUserRepository recodyUserRepository;
    
    @Value("${users.admin.username}")
    private String username;
    
    @Value("${users.admin.password}")
    private String password;
    
    @Value("${users.admin.email}")
    private String email;
    
    @PostConstruct
    void register(){
        Optional<RecodyUser> optionalUser = recodyUserRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            log.info("이미 어드민 유저가 있음. optionalUser: {}", optionalUser);
        }
        RecodyUser adminUser = RecodyUser.builder().userId(1L).username(username).password(password).email(email).socialType(
                SocialProvider.NONE).role(Role.ROLE_ADMIN).build();
        RecodyUser savedAdmin = recodyUserRepository.save(adminUser);
        log.info("어드민 유저 등록 성공 savedAdmin: {}", savedAdmin);
    }
}
