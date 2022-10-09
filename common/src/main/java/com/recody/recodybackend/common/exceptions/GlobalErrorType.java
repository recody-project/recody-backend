package com.recody.recodybackend.common.exceptions;

public enum GlobalErrorType implements ErrorType{
    UserNotFound("UserNotFound"),
    
    ContentNotFound("ContentNotFound"),
    FailedToResolveAccessToken("FailedToResolveAccessToken"),
    InternalServerError("InternalServerError"),
    InsufficientAuthentication("InsufficientAuthentication"),
    UnsupportedCategory("UnsupportedCategory")
    ;
    
    private final String errorCode;
    
    
    GlobalErrorType(String errorCode) { this.errorCode = errorCode; }
    
    
    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
