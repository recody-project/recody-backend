package com.recody.recodybackend.users.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.GlobalErrorType;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {
    
    public UserNotFoundException() {
        super(GlobalErrorType.UserNotFound, HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
    }
}
