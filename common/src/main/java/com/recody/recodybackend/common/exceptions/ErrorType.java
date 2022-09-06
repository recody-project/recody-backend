package com.recody.recodybackend.common.exceptions;

/*
* 애플리케이션에서 일어날 수 있는 에러의 종류를 정의합니다.
*
*
* errorCode: 에러 종류에 따라 더 자세한 설명을 제공하기 위한 식별 코드.
*   현재는 ErrorType 과 같은 값으로 정의하고 있으며, 필요에 따라 세분화할 에정.
*
* TODO: 1. 서비스 마다 각각의 에러 타입 정의하기
*       2. 에러 코드 세분화하기 */
public enum ErrorType {
    // Users
    RefreshTokenNotFound("RefreshTokenNotFound"),
    InvalidUserAgentValue("InvalidUserAgentValue"),
    UserNotFound("UserNotFound"),
    FailedToResolveAccessToken("FailedToResolveAccessToken"),
    SocialAccessTokenExpired("SocialAccessTokenExpired"),
    
    // Region
    RegionNotFound("RegionNotFound"),
    RegionCodeNotFound("RegionCodeNotFound"),
    
    
    // Community
    CommentNotFound("CommentNotFound"),
    
    ;
    
    private final String errorCode;
    
    ErrorType(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() { return errorCode; }
}
