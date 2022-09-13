package com.recody.recodybackend.users.features.login.naverlogin;

import com.recody.recodybackend.users.features.login.GetUserInfoFromResourceServer;

public interface NaverClient {
    
    NaverOAuthResponse handle(GetUserInfoFromResourceServer command);
    
    
    RefreshNaverAccessTokenResponse handle(RefreshNaverAccessToken command);
}
