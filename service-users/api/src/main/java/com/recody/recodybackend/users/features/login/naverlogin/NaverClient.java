package com.recody.recodybackend.users.features.login.naverlogin;

import com.recody.recodybackend.users.features.login.JacksonOAuthAttributes;
import com.recody.recodybackend.users.features.login.ResourceAccessToken;
import com.recody.recodybackend.users.features.login.ResourceRefreshToken;
import com.recody.recodybackend.users.features.login.SocialLoginClient;

public interface NaverClient extends SocialLoginClient {
    
    JacksonOAuthAttributes getUserInfo(ResourceAccessToken accessToken);
    
    ResourceAccessToken refreshResourceAccessToken(ResourceRefreshToken refreshToken);
}
