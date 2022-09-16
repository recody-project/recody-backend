package com.recody.recodybackend.users.features.login.fetchuserinfo;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.users.exceptions.ResourceAccessTokenExpiredException;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import com.recody.recodybackend.users.features.login.JacksonOAuthAttributes;
import com.recody.recodybackend.users.features.login.ResourceAccessToken;
import com.recody.recodybackend.users.features.login.ResourceRefreshToken;
import com.recody.recodybackend.users.features.login.SocialProvider;
import com.recody.recodybackend.users.features.login.googlelogin.GoogleClient;
import com.recody.recodybackend.users.features.login.SocialLoginClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultFetchUserInfoHandler implements FetchUserInfoHandler{
    
    private final GoogleClient googleClient;
//    private final NaverClient naverClient;
//    private final KakaoClient kakaoClient;
    
    @Override
    public JacksonOAuthAttributes handle(FetchUserInfo command) {
    
        JacksonOAuthAttributes userInfo;
        ResourceAccessToken resourceAccessToken = command.getResourceAccessToken();
        ResourceRefreshToken resourceRefreshToken = command.getResourceRefreshToken();
        SocialProvider socialProvider = command.getSocialProvider();
        
        switch (socialProvider){
            case GOOGLE:
                userInfo = doFetchUserInfo(googleClient, resourceAccessToken, resourceRefreshToken);
                break;
//            case NAVER:
//                userInfo = doFetchUserInfo(naverClient, resourceAccessToken, resourceRefreshToken);
//                break;
            default:
                throw new ApplicationException(UsersErrorType.UnsupportedSocialLoginService,
                                               HttpStatus.BAD_REQUEST,
                                               "지원하지 않는 로그인 서비스입니다.: " + socialProvider);
        }
        return userInfo;
    }
    
    
    private JacksonOAuthAttributes doFetchUserInfo(SocialLoginClient client, ResourceAccessToken resourceAccessToken, ResourceRefreshToken resourceRefreshToken) {
        JacksonOAuthAttributes userInfo;
        try {
            userInfo = client.getUserInfo(resourceAccessToken);
            log.debug("client 가 유저정보를 가져왔습니다.");
            log.trace("userInfo: {}", userInfo);
        } catch (ResourceAccessTokenExpiredException exception) {
            log.info("리소스 엑세스 토큰이 만료됨 : {}", exception.getMessage());
            // 리프레시 토큰이 없으면 더이상 처리할 수 없다.
            if (resourceRefreshToken == null) {
                throw new ApplicationException(UsersErrorType.SocialLoginFailed,
                                               HttpStatus.UNAUTHORIZED);
            }
    
            // 액세스 토큰을 갱신해본다.
            ResourceAccessToken
                    newResourceAccessToken = client.refreshResourceAccessToken(resourceRefreshToken);
        
            try {
                userInfo = client.getUserInfo(newResourceAccessToken);
            } catch (ResourceAccessTokenExpiredException e) {
                log.info("리소스 액세스 토큰이 유효하지 않음. 갱신도 불가.");
                throw new ApplicationException(UsersErrorType.SocialLoginFailed,
                                               HttpStatus.UNAUTHORIZED);
            }
        }
        return userInfo;
    }
}
