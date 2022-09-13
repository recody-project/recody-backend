package com.recody.recodybackend.users.features.login.google;

import com.recody.recodybackend.users.features.login.GetUserInfo;
import com.recody.recodybackend.users.features.login.JacksonOAuthAttributes;

public interface GoogleClient {
    
    JacksonOAuthAttributes getUserInfo(GetUserInfo command);
}
