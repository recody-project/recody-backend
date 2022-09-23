package com.recody.recodybackend.common.exceptions;

public enum GlobalErrorType implements ErrorType{
    FailedToResolveAccessToken("FailedToResolveAccessToken"),
    InternalServerError("InternalServerError"),
    InsufficientAuthentication("InsufficientAuthentication")
    ;
    
    private final String errorCode;
    
    
    GlobalErrorType(String errorCode) { this.errorCode = errorCode; }
    
    
    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
