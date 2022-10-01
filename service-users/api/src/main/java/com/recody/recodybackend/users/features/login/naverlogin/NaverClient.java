package com.recody.recodybackend.users.features.login.naverlogin;

import com.recody.recodybackend.users.features.login.*;

public interface NaverClient extends SocialLoginClient {
    
    @Override
    OAuthUserInfo getUserInfo(ResourceAccessToken accessToken);
    
    ResourceAccessToken refreshResourceAccessToken(ResourceRefreshToken refreshToken);
}
