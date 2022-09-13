package com.recody.recodybackend.users.features.login;

import com.recody.recodybackend.users.data.*;
import com.recody.recodybackend.users.features.generatenickname.NicknameGenerator;
import com.recody.recodybackend.users.features.jwt.CreateAccessToken;
import com.recody.recodybackend.users.features.jwt.CreateRefreshToken;
import com.recody.recodybackend.users.features.jwt.JwtManager;
import com.recody.recodybackend.users.features.login.googlelogin.GoogleClient;
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
    public ProcessLoginResponse handleNaverLogin(ProcessLogin command) {
        return ProcessLoginResponse.builder()
                                   .accessToken("awetaw")
                                   .refreshToken("refref").build();
    }
    
    @Override
    @Transactional
    public ProcessLoginResponse handleGoogleLogin(ProcessLogin command) {
        JacksonOAuthAttributes userInfo = googleClient.getUserInfo(
                GetUserInfoFromResourceServer.builder().resourceAccessToken(command.getResourceAccessToken()).build());
        log.debug("userInfo: {}", userInfo);
    
        // 유저 정보를 조회하여 없으면 회원가입 시킨다.
        RecodyUser targetUser = recodyUserRepository.getByEmail(userInfo.getEmail());
        
        if (targetUser == null) {
            targetUser = signUpUser(userInfo, SocialProvider.GOOGLE);
            log.info("회원가입 되었습니다.: {}", targetUser);
        }
        
        // 있으면 바로 토큰을 발급하여 반환한다.
        String accessToken = jwtManager.createAccessToken(
                CreateAccessToken.builder().userId(targetUser.getUserId()).email(userInfo.getEmail()).build());
        String refreshToken = jwtManager.createRefreshToken(
                CreateRefreshToken.builder().userId(targetUser.getUserId()).email(userInfo.getEmail()).build());
        ProcessLoginResponse response = createSocialLoginResponse(accessToken, refreshToken);
    
        saveRefreshToken(command, userInfo, refreshToken);
        log.info("{} 님의 로그인 정보를 반환합니다.: {}", userInfo.getName(), response);
        return response;
    }
    
    @Override
    public ProcessLoginResponse handleKakaoLogin(ProcessLogin command) {
        return null;
    }
    
    private void saveRefreshToken(ProcessLogin command, JacksonOAuthAttributes userInfo, String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                                                                  .refreshTokenValue(refreshToken)
                                                                  .userAgent(command.getUserAgent())
                                                                  .subject(userInfo.getEmail()).build();
        refreshTokenRepository.save(refreshTokenEntity);
    }
    
    private RecodyUser signUpUser(JacksonOAuthAttributes userInfo, SocialProvider socialProvider) {
        RecodyUser targetUser;
        targetUser = RecodyUser.builder()
                               .email(userInfo.getEmail())
                               .socialType(socialProvider)
                               .nickname(nicknameGenerator.randomNickname())
                               .role(Role.ROLE_MEMBER).pictureUri(userInfo.getProfileImageUrl())
                               .username(userInfo.getName()).build();
        recodyUserRepository.save(targetUser);
        return targetUser;
    }
    
    private ProcessLoginResponse createSocialLoginResponse(String accessToken, String refreshToken) {
        return ProcessLoginResponse.builder()
                                   .accessToken(accessToken)
                                   .refreshToken(refreshToken)
                                   .accessExpireTime(jwtManager.getExpireTimeFromToken(accessToken))
                                   .refreshExpireTime(jwtManager.getExpireTimeFromToken(refreshToken)).build();
    }
    
    
}
