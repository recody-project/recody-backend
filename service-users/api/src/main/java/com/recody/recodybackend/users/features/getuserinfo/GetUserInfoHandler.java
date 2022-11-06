package com.recody.recodybackend.users.features.getuserinfo;

import com.recody.recodybackend.users.RecodyUserInfo;

public interface GetUserInfoHandler {
    
    RecodyUserInfo handle(GetUserInfo command);
    
}
