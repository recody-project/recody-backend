package com.recody.recodybackend.users.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class AdminAuthorizationRequiredException extends ApplicationException {
    
    public AdminAuthorizationRequiredException() {
        super( UsersErrorType.AdminAuthorizationRequired, HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다." );
    }
}
