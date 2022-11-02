package com.recody.recodybackend.users.features.login;

import com.recody.recodybackend.users.OAuthUserInfo;
import com.recody.recodybackend.users.exceptions.ResourceAccessTokenExpiredException;
import com.recody.recodybackend.users.sociallogin.ResourceAccessToken;
import com.recody.recodybackend.users.sociallogin.ResourceRefreshToken;

public interface SocialLoginClient {
    
    OAuthUserInfo getUserInfo(ResourceAccessToken accessToken) throws ResourceAccessTokenExpiredException;
    
    ResourceAccessToken refreshResourceAccessToken(ResourceRefreshToken refreshToken);
    
}
