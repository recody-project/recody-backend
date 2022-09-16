package com.recody.recodybackend.users.exceptions;


import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class FailedToResolveAccessTokenException extends ApplicationException {
    public FailedToResolveAccessTokenException() {
        super(UsersErrorType.FailedToResolveAccessToken, HttpStatus.UNAUTHORIZED);
    }
}
