package com.recody.recodybackend.users.features.login.admin;

import com.recody.recodybackend.users.RecodySignInSession;

public interface SignInAdminUserHandler {
    
    
    RecodySignInSession handle(SignInAdminUser command);
    
}
