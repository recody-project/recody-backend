package com.recody.recodybackend.users.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.ErrorType;
import org.springframework.http.HttpStatus;

public class SocialAccessTokenExpiredException extends ApplicationException {
    
    public SocialAccessTokenExpiredException() {
        super(ErrorType.SocialAccessTokenExpired, HttpStatus.BAD_REQUEST);
    }
}
