package com.recody.recodybackend.users.features.signin.kakaologin;

import com.recody.recodybackend.users.OAuthUserInfo;
import com.recody.recodybackend.users.sociallogin.ResourceAccessToken;
import com.recody.recodybackend.users.features.signin.SocialLoginClient;

public interface KakaoClient extends SocialLoginClient {
    @Override
    OAuthUserInfo getUserInfo(ResourceAccessToken accessToken);
    
}
