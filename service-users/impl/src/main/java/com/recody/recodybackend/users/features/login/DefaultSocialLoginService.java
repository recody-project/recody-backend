package com.recody.recodybackend.users.features.login;

import com.recody.recodybackend.users.data.*;
import com.recody.recodybackend.users.features.generatenickname.NicknameGenerator;
import com.recody.recodybackend.users.features.jwt.CreateAccessToken;
import com.recody.recodybackend.users.features.jwt.CreateRefreshToken;
import com.recody.recodybackend.users.features.jwt.JwtManager;
import com.recody.recodybackend.users.features.login.google.GoogleClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class DefaultSocialLoginService implements SocialLoginService {
    
    private final JwtManager jwtManager;
    private final GoogleClient googleClient;
    private final RecodyUserRepository recodyUserRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final NicknameGenerator nicknameGenerator;
    
    @Override
    public SocialLoginResponse loginNaver(SocialLoginRequest request) {
        return SocialLoginResponse.builder()
                                  .accessToken("awetaw")
                                  .refreshToken("refref").build();
    }
    
    @Override
    @Transactional
    public SocialLoginResponse loginGoogle(ProcessLogin command) {
        JacksonOAuthAttributes userInfo = googleClient.getUserInfo(GetUserInfo.builder().accessToken(command.getAccessToken()).build());
        log.debug("userInfo: {}", userInfo);
    
        // 유저 정보를 조회하여 없으면 회원가입 시킨다.
        RecodyUser targetUser = recodyUserRepository.getByEmail(userInfo.getEmail());
        
        if (targetUser == null) {
            targetUser = RecodyUser.builder()
                                 .email(userInfo.getEmail()).socialType(SocialProvider.GOOGLE).nickname(nicknameGenerator.randomNickname())
                                 .role(Role.ROLE_MEMBER).pictureUri(userInfo.getProfileImageUrl()).username(userInfo.getName()).build();
            recodyUserRepository.save(targetUser);
            log.info("회원가입 되었습니다.: {}", targetUser);
        }
        
        // 있으면 바로 토큰을 발급하여 반환한다.
        String accessToken = jwtManager.createAccessToken(
                CreateAccessToken.builder().userId(targetUser.getUserId()).email(userInfo.getEmail()).build());
        String refreshToken = jwtManager.createRefreshToken(
                CreateRefreshToken.builder().userId(targetUser.getUserId()).email(userInfo.getEmail()).build());
    
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                                                                  .refreshTokenValue(refreshToken)
                                                                  .userAgent(command.getUserAgent())
                                                                  .subject(userInfo.getEmail()).build();
        refreshTokenRepository.save(refreshTokenEntity);
        SocialLoginResponse response = createSocialLoginResponse(accessToken, refreshToken);
        log.info("{} 님의 로그인 정보를 반환합니다.: {}", userInfo.getName(), response);
        return response;
    }
    
    private SocialLoginResponse createSocialLoginResponse(String accessToken, String refreshToken) {
        return SocialLoginResponse.builder()
                                  .accessToken(accessToken)
                                  .refreshToken(refreshToken)
                                  .accessExpireTime(jwtManager.getExpireTimeFromToken(accessToken))
                                  .refreshExpireTime(jwtManager.getExpireTimeFromToken(refreshToken)).build();
    }
    
    
}
