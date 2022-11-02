package com.recody.recodybackend.users.features.login.admin;

import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.commonbootutils.jwt.CreateAccessToken;
import com.recody.recodybackend.commonbootutils.jwt.CreateRefreshToken;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.users.data.RefreshTokenRepository;
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
    private final RefreshTokenRepository refreshTokenRepository;
    
    @Override
    public SignInAdminUserResponse handle(SignInAdminUser command) {
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
        SignInAdminUserResponse response = createSignInRecodyUserResponse(adminUser);
        log.info("어드민 로그인 성공: {}", response);
        return response;
    }
    
    private SignInAdminUserResponse createSignInRecodyUserResponse(RecodyUserEntity recodyUserEntity) {
        String accessToken = jwtManager.createAccessToken(
                CreateAccessToken.builder().userId( recodyUserEntity.getUserId() ).email( recodyUserEntity.getEmail() ).build() );
        String refreshToken = jwtManager.createRefreshToken(
                CreateRefreshToken.builder().userId( recodyUserEntity.getUserId() ).email( recodyUserEntity.getEmail() ).build() );
        //TODO: 리프레시 토큰 저장 로직
        
        return SignInAdminUserResponse
                .builder()
                .role( recodyUserEntity.getRole() )
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpireTime(jwtManager.getExpireTimeFromToken(accessToken))
                .refreshExpireTime(jwtManager.getExpireTimeFromToken(refreshToken))
                .build();
    }
}
