package com.recody.recodybackend.users.features.signin.admin;

import com.recody.recodybackend.users.RecodySignInSession;
import com.recody.recodybackend.users.RecodyUserInfo;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserMapper;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.features.signin.membership.MembershipManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSignInAdminUserHandler implements SignInAdminUserHandler{
    private final RecodyUserRepository recodyUserRepository;
    
    private final MembershipManager membershipManager;
    
    private final RecodyUserMapper recodyUserMapper;
    
    @Override
    public RecodySignInSession handle(SignInAdminUser command) {
        String username = command.getUsername();
        Optional<RecodyUserEntity> optionalUser = recodyUserRepository.findByUsername( username );
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        RecodyUserEntity adminUser = optionalUser.get();
        String password = adminUser.getPassword();
        if (!password.equals(command.getPassword())) {
            throw new IllegalArgumentException("패스워드가 틀림.");
        }
        log.info("어드민 유저 확인됨. username: {}", username);
        RecodyUserInfo userInfo = recodyUserMapper.map( adminUser );
        RecodySignInSession sessionInfo = membershipManager.createSessionInfo( userInfo, "admin");
        log.info("어드민 로그인 성공: {}", sessionInfo);
        return sessionInfo;
    }
}
