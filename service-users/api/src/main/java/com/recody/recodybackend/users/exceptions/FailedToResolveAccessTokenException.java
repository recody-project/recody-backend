package com.recody.recodybackend.users.exceptions;


import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.ErrorType;
import org.springframework.http.HttpStatus;

public class FailedToResolveAccessTokenException extends ApplicationException {
    public FailedToResolveAccessTokenException() {
        super(ErrorType.FailedToResolveAccessToken, HttpStatus.UNAUTHORIZED);
    }
}
