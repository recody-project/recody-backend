package com.recody.recodybackend.movie.exceptions;

import com.recody.recodybackend.common.exceptions.ErrorType;

public enum MovieErrorType implements ErrorType {
    UnsupportedMovieSource("UnsupportedMovieSource"),
    NoMovieTitleFound("NoMovieTitleFound")
    ;
    
    private final String errorCode;
    
    MovieErrorType(String errorCode) {
        this.errorCode = errorCode;
    }
    
    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
