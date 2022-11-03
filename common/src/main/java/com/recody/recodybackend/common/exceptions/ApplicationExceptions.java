package com.recody.recodybackend.common.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class ApplicationExceptions {
    
    public static void requireNonNull(String value, ErrorType errorType, String message) {
        if ( Objects.isNull(value) ) {
            throw new ApplicationException(errorType, HttpStatus.BAD_REQUEST, message);
        }
    }
    
    public static void requireNonNull(String value, ErrorType errorType) {
        if ( Objects.isNull(value) ) {
            throw new ApplicationException(errorType, HttpStatus.BAD_REQUEST);
        }
    }
    
    public static ApplicationException badRequestOf(ErrorType errorType) {
        return new ApplicationException(errorType, HttpStatus.BAD_REQUEST, errorType.name());
    }
    
    public static ApplicationException badRequestOf(ErrorType errorType, String message) {
        return new ApplicationException(errorType, HttpStatus.BAD_REQUEST, message);
    }
}
