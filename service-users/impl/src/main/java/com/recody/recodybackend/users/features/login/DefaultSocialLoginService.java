package com.recody.recodybackend.users.features.login;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.users.data.*;
import com.recody.recodybackend.users.exceptions.ResourceAccessTokenExpiredException;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import com.recody.recodybackend.users.features.generatenickname.NicknameGenerator;
import com.recody.recodybackend.users.features.jwt.CreateAccessToken;
import com.recody.recodybackend.users.features.jwt.CreateRefreshToken;
import com.recody.recodybackend.users.features.jwt.JwtManager;
import com.recody.recodybackend.users.features.login.fetchuserinfo.FetchUserInfo;
import com.recody.recodybackend.users.features.login.fetchuserinfo.FetchUserInfoHandler;
import com.recody.recodybackend.users.features.login.googlelogin.GoogleClient;
import com.recody.recodybackend.users.features.login.googlelogin.RefreshGoogleAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class DefaultSocialLoginService implements SocialLoginService {
    
    private final JwtManager jwtManager;
    private final RecodyUserRepository recodyUserRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final NicknameGenerator nicknameGenerator;
    private final FetchUserInfoHandler fetchUserInfoHandler;
    
    @Override
    public ProcessLoginResponse handleNaverLogin(ProcessLogin command) {
        return ProcessLoginResponse.builder()
                                   .accessToken("awetaw")
                                   .refreshToken("refref").build();
    }
    
    @Override
    @Transactional
    public ProcessLoginResponse handleGoogleLogin(ProcessLogin command) {
        String resourceAccessTokenValue = command.getResourceAccessToken();
        String resourceRefreshTokenValue = command.getResourceRefreshToken();
        
        // 구글에서 유저 정보를 가져온다. 실패시 리프레시 토큰이 있는지 확인하고 갱신해본다.
        JacksonOAuthAttributes
                userInfo = fetchUserInfoHandler.handle(FetchUserInfo
                                                           .builder()
                                                           .resourceAccessToken(ResourceAccessToken.googleOf(resourceAccessTokenValue))
                                                           .resourceRefreshToken(ResourceRefreshToken.googleOf(resourceRefreshTokenValue))
                                                           .socialProvider(SocialProvider.GOOGLE)
                                                           .build());
    
        // 유저 정보를 조회하여 없으면 회원가입 시킨다.
        Optional<RecodyUser> optionalUser = recodyUserRepository.findByEmail(userInfo.getEmail());
        RecodyUser recodyUser;
        if (optionalUser.isEmpty()) {
            recodyUser = signUpUser(userInfo, SocialProvider.GOOGLE);
            log.info("회원가입 되었습니다.: {}", optionalUser);
        } else {
            recodyUser = optionalUser.get();
        }
        
        String accessToken = jwtManager.createAccessToken(
                CreateAccessToken.builder().userId(recodyUser.getUserId()).email(userInfo.getEmail()).build());
        String refreshToken = jwtManager.createRefreshToken(
                CreateRefreshToken.builder().userId(recodyUser.getUserId()).email(userInfo.getEmail()).build());
        ProcessLoginResponse response = createProcessLoginResponse(accessToken, refreshToken);
    
        saveRefreshToken(command, userInfo, refreshToken);
        log.info("{} 님의 로그인 정보를 반환합니다.: {}", userInfo.getName(), response);
        return response;
    }
    
    @Override
    public ProcessLoginResponse handleKakaoLogin(ProcessLogin command) {
        return null;
    }
    
    /*
    * 리프레시 토큰을 저장한다.
    * 리프레시 토큰은 user agent, 식별 정보, resource refresh token 을 포함한다. */
    private void saveRefreshToken(ProcessLogin command, JacksonOAuthAttributes userInfo, String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                                                                  .refreshTokenValue(refreshToken)
                                                                  .userAgent(command.getUserAgent())
                                                                  .resourceRefreshToken(command.getResourceRefreshToken())
                                                                  .subject(userInfo.getEmail()).build();
        refreshTokenRepository.save(refreshTokenEntity);
    }
    
    private RecodyUser signUpUser(JacksonOAuthAttributes userInfo, SocialProvider socialProvider) {
        RecodyUser targetUser;
        targetUser = RecodyUser
                .builder()
                .email(userInfo.getEmail())
                .socialType(socialProvider)
                .nickname(nicknameGenerator.randomNickname())
                .role(Role.ROLE_MEMBER)
                .pictureUri(userInfo.getProfileImageUrl())
                .username(userInfo.getName())
                .build();
        recodyUserRepository.save(targetUser);
        return targetUser;
    }
    
    
    private ProcessLoginResponse createProcessLoginResponse(String accessToken, String refreshToken) {
        return ProcessLoginResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpireTime(jwtManager.getExpireTimeFromToken(accessToken))
                .refreshExpireTime(jwtManager.getExpireTimeFromToken(refreshToken))
                .build();
    }
    
    
}
