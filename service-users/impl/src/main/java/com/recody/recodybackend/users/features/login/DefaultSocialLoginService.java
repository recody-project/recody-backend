package com.recody.recodybackend.users.features.login;

import com.recody.recodybackend.users.*;
import com.recody.recodybackend.users.features.jwt.refreshtoken.RefreshTokenManager;
import com.recody.recodybackend.users.features.login.fetchuserinfo.FetchUserInfo;
import com.recody.recodybackend.users.features.login.fetchuserinfo.FetchUserInfoHandler;
import com.recody.recodybackend.users.features.login.membership.MembershipManager;
import com.recody.recodybackend.users.features.login.normalsignin.SignInUserHandler;
import com.recody.recodybackend.users.sociallogin.ResourceAccessToken;
import com.recody.recodybackend.users.sociallogin.ResourceRefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class DefaultSocialLoginService implements SocialLoginService {
    
    private final RefreshTokenManager refreshTokenManager;
    private final FetchUserInfoHandler fetchUserInfoHandler;
    private final MembershipManager membershipManager;
    
    @Override
    public RecodySignInSession handleNaverLogin(ProcessSocialLogin command) {
        return RecodySignInSession.builder().accessToken("awetaw").refreshToken("refref").build();
    }
    
    @Override
    public RecodySignInSession handleGoogleLogin(ProcessSocialLogin command) {
        OAuthUserInfo userInfo = fetchUserInfoHandler.handle( createGoogleFetchUserInfoCommand( command ) );
        RecodyUserInfo recodyUserInfo = membershipManager.signUp(userInfo);
        RecodySignInSession session = membershipManager.createSessionInfo(recodyUserInfo, command.getUserAgent());
        refreshTokenManager.integrate(session, command.getUserAgent());
        log.info("{} 님의 로그인 정보를 반환합니다.: {}", userInfo.getName(), session);
        
        return session;
    }
    
    @Override
    public RecodySignInSession handleKakaoLogin(ProcessSocialLogin command) {
        return null;
    }
    
    
    private FetchUserInfo createGoogleFetchUserInfoCommand(ProcessSocialLogin command) {
        String resourceAccessTokenValue = command.getResourceAccessToken();
        String resourceRefreshTokenValue = command.getResourceRefreshToken();
        
        return FetchUserInfo
                .builder()
                .resourceAccessToken( ResourceAccessToken.googleOf( resourceAccessTokenValue ) )
                .resourceRefreshToken( ResourceRefreshToken.googleOf( resourceRefreshTokenValue ) )
                .socialProvider( SocialProvider.GOOGLE )
                .build();
    }
}
