package com.recody.recodybackend.users.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {
    
    public UserNotFoundException() {
        super(UsersErrorType.UserNotFound, HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
    }
}
