package com.recody.recodybackend.users.features.login.kakaologin;

import com.recody.recodybackend.users.features.login.GetUserInfoFromResourceServer;
import com.recody.recodybackend.users.features.login.JacksonOAuthAttributes;

public interface KakaoClient {
    JacksonOAuthAttributes handle(GetUserInfoFromResourceServer command);
    
}
