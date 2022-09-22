package com.recody.recodybackend.commonbootutils.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.GlobalErrorType;
import org.springframework.http.HttpStatus;

public class FailedToResolveAccessTokenException extends ApplicationException {
    
    public FailedToResolveAccessTokenException() {
        super(GlobalErrorType.FailedToResolveAccessToken, HttpStatus.UNAUTHORIZED);
    }
}
