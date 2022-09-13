package com.recody.recodybackend.users.features.login.googlelogin;

import com.recody.recodybackend.users.features.login.GetUserInfoFromResourceServer;
import com.recody.recodybackend.users.features.login.JacksonOAuthAttributes;

public interface GoogleClient {
    
    JacksonOAuthAttributes getUserInfo(GetUserInfoFromResourceServer command);
    
    RefreshGoogleAccessTokenResponse handle(RefreshGoogleAccessToken command);
}
