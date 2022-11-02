package com.recody.recodybackend.users.features.login.googlelogin;

import com.recody.recodybackend.users.exceptions.ResourceAccessTokenExpiredException;
import com.recody.recodybackend.users.sociallogin.ResourceAccessToken;
import com.recody.recodybackend.users.sociallogin.ResourceRefreshToken;
import com.recody.recodybackend.users.features.login.SocialLoginClient;

public interface GoogleClient extends SocialLoginClient {
    /*
    * 유저 정보를 구글 리소스 서버에서 받아온다. */
    @Override
    GoogleOAuthUserInfo getUserInfo(ResourceAccessToken accessToken) throws ResourceAccessTokenExpiredException;
    
    /*
    * 리소스 액세스 토큰을 갱신한다.
    * 리소스 리프레시 토큰은 유저가 로그인할 때 받아서 db 에 저장해둔 것을 사용한다.
    * 리프레시 토큰의 시간이 만료되었다면 예외를 발생시킨다.
    * -> 외부의 로그인 로직에서 해당 예외를 바탕으로 로그아웃 처리를하여 유저가 다시 로그인을 시도하게끔 만든다. */
    ResourceAccessToken refreshResourceAccessToken(ResourceRefreshToken refreshToken);
}
