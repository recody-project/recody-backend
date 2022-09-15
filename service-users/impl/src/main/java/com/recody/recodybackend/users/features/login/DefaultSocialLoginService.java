package com.recody.recodybackend.users.features.login;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.users.data.*;
import com.recody.recodybackend.users.exceptions.SocialAccessTokenExpiredException;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import com.recody.recodybackend.users.features.generatenickname.NicknameGenerator;
import com.recody.recodybackend.users.features.jwt.CreateAccessToken;
import com.recody.recodybackend.users.features.jwt.CreateRefreshToken;
import com.recody.recodybackend.users.features.jwt.JwtManager;
import com.recody.recodybackend.users.features.login.googlelogin.GoogleClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

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
        String resourceAccessToken = command.getResourceAccessToken();
        String resourceRefreshToken = command.getResourceRefreshToken();
        
        // 구글에서 유저 정보를 가져온다. 실패시 리프레시 토큰이 있는지 확인하고 갱신해본다.
        // GetUserInfoWithAccessToken, RefreshAccessToken, FindRefreshToken,
        JacksonOAuthAttributes userInfo;
        try {
            userInfo = googleClient.getUserInfo(
                    GetUserInfoFromResourceServer.builder().resourceAccessToken(resourceAccessToken).build());
            log.debug("userInfo: {}", userInfo);
        } catch (RestClientException exception){
            log.info("구글 리소스 서버에서 유저 정보를 받아오지 못했습니다. : {}", exception.getMessage());
            throw new SocialAccessTokenExpiredException();
        }
        
        if (resourceRefreshToken != null) {
            // 액세스 토큰을 갱신해본다.
        }
        
    
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
