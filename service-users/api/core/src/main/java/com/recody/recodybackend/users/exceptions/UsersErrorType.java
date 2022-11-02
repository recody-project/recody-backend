package com.recody.recodybackend.users.exceptions;

import com.recody.recodybackend.common.exceptions.ErrorType;

public enum UsersErrorType implements ErrorType {
    UserNotFound("UserNotFound"),
    UserAlreadyExists("UserAlreadyExists"),
    EmailShouldNotBeNull("users.email.not-null"),
    PasswordConfirmNotMatch("users.signup.password.not-match"),
    RefreshTokenNotFound("RefreshTokenNotFound"),
    InvalidUserAgentValue("InvalidUserAgentValue"),
    FailedToResolveAccessToken("FailedToResolveAccessToken"),
    ResourceAccessTokenExpired("ResourceAccessTokenExpired"),
    ResourceRefreshTokenExpired("ResourceRefreshTokenExpired"),
    CannotRefreshResourceAccessToken("CannotRefreshResourceAccessToken"),
    SocialLoginFailed("SocialLoginFailed"),
    UnsupportedSocialLoginService("UnsupportedSocialLoginService")
    
    
    
    ;
    
    private final String errorCode;
    
    
    UsersErrorType(String errorCode) { this.errorCode = errorCode; }
    
    
    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
