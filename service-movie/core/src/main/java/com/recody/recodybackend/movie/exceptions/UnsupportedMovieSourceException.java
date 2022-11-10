package com.recody.recodybackend.movie.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class UnsupportedMovieSourceException extends ApplicationException {
    
    public UnsupportedMovieSourceException() {
        super(MovieErrorType.UnsupportedMovieSource, HttpStatus.BAD_REQUEST, "지원하지 않는 영화 출처입니다.");
    }
}
