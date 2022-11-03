package com.recody.recodybackend.users.features.signin.adminsignin;

import com.recody.recodybackend.users.RecodySignInSession;

public interface SignInAdminUserHandler {
    
    
    RecodySignInSession handle(SignInAdminUser command);
    
}
