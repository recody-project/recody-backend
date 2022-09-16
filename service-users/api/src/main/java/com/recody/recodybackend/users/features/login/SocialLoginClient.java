package com.recody.recodybackend.users.features.login;

import com.recody.recodybackend.users.exceptions.ResourceAccessTokenExpiredException;

public interface SocialLoginClient {
    
    JacksonOAuthAttributes getUserInfo(ResourceAccessToken accessToken) throws ResourceAccessTokenExpiredException;
    
    ResourceAccessToken refreshResourceAccessToken(ResourceRefreshToken refreshToken);
    
}
