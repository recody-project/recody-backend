package com.recody.recodybackend.users.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class SocialAccessTokenExpiredException extends ApplicationException {
    
    public SocialAccessTokenExpiredException() {
        super(UsersErrorType.SocialAccessTokenExpired, HttpStatus.BAD_REQUEST);
    }
}
