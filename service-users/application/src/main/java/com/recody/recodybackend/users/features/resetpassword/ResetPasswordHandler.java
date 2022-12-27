package com.recody.recodybackend.users.features.resetpassword;

import com.recody.recodybackend.users.RecodyUserInfo;

public interface ResetPasswordHandler {
    
    RecodyUserInfo handle(ResetPassword command);
    
}
