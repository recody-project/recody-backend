package com.recody.recodybackend.users.features.login.kakaologin;

import com.recody.recodybackend.users.features.login.JacksonOAuthAttributes;
import com.recody.recodybackend.users.features.login.ResourceAccessToken;
import com.recody.recodybackend.users.features.login.SocialLoginClient;

public interface KakaoClient extends SocialLoginClient {
    JacksonOAuthAttributes getUserInfo(ResourceAccessToken accessToken);
    
}
