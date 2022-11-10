package com.recody.recodybackend.movie.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class NoMovieTitleFoundException extends ApplicationException {
    
    public NoMovieTitleFoundException() {
        super(MovieErrorType.NoMovieTitleFound, HttpStatus.INTERNAL_SERVER_ERROR, "영화 제목을 못찾았습니다.");
    }
}
