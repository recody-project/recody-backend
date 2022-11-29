package com.recody.recodybackend.users.features.signin.naverlogin;

import com.recody.recodybackend.users.OAuthUserInfo;
import com.recody.recodybackend.users.features.signin.*;
import com.recody.recodybackend.users.sociallogin.ResourceAccessToken;
import com.recody.recodybackend.users.sociallogin.ResourceRefreshToken;

public interface NaverClient extends SocialLoginClient {
    
    @Override
    OAuthUserInfo getUserInfo(ResourceAccessToken accessToken);
    
    ResourceAccessToken refreshResourceAccessToken(ResourceRefreshToken refreshToken);
}
