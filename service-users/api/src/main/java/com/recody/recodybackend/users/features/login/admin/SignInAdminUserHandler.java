package com.recody.recodybackend.users.features.login.admin;

public interface SignInAdminUserHandler {
    
    SignInAdminUserResponse handle(SignInAdminUser command);
    
}
