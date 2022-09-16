package com.recody.recodybackend.users.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class ResourceAccessTokenExpiredException extends Exception {
    
//    public SocialAccessTokenExpiredException() {
//        super(UsersErrorType.SocialAccessTokenExpired, HttpStatus.BAD_REQUEST);
//    }
    
    
    public ResourceAccessTokenExpiredException() {
        super("리소스 엑세스 토큰이 만료됨");
    }
}
