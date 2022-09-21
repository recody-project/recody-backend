package com.recody.recodybackend.users.features.login.admin;

import com.recody.recodybackend.users.data.RecodyUser;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.features.jwt.CreateAccessToken;
import com.recody.recodybackend.users.features.jwt.CreateRefreshToken;
import com.recody.recodybackend.users.features.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSignInAdminUserHandler implements SignInAdminUserHandler{
    
    private final JwtManager jwtManager;
    private final RecodyUserRepository recodyUserRepository;
    
    @Override
    public SignInAdminUserResponse handle(SignInAdminUser command) {
        String username = command.getUsername();
        Optional<RecodyUser> optionalUser = recodyUserRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        RecodyUser adminUser = optionalUser.get();
        String password = adminUser.getPassword();
        if (!password.equals(command.getPassword())) {
            throw new IllegalArgumentException("패스워드가 틀림.");
        }
        log.info("어드민 유저 확인됨. username: {}", username);
        SignInAdminUserResponse response = createSignInRecodyUserResponse(adminUser);
        log.info("어드민 로그인 성공: {}", response);
        return response;
    }
    
    private SignInAdminUserResponse createSignInRecodyUserResponse(RecodyUser recodyUser) {
        String accessToken = jwtManager.createAccessToken(
                CreateAccessToken.builder().userId(recodyUser.getUserId()).email(recodyUser.getEmail()).build());
        String refreshToken = jwtManager.createRefreshToken(
                CreateRefreshToken.builder().userId(recodyUser.getUserId()).email(recodyUser.getEmail()).build());
        //TODO: 리프레시 토큰 저장 로직
        return SignInAdminUserResponse
                .builder()
                .role(recodyUser.getRole())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpireTime(jwtManager.getExpireTimeFromToken(accessToken))
                .refreshExpireTime(jwtManager.getExpireTimeFromToken(refreshToken))
                .build();
    }
}
