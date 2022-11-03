package com.recody.recodybackend.users.exceptions;

public class ResourceAccessTokenExpiredException extends Exception {
    
//    public SocialAccessTokenExpiredException() {
//        super(UsersErrorType.SocialAccessTokenExpired, HttpStatus.BAD_REQUEST);
//    }
    
    
    public ResourceAccessTokenExpiredException() {
        super("리소스 엑세스 토큰이 만료됨");
    }
}
