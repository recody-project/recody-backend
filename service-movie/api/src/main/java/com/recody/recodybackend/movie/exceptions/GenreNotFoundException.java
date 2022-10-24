package com.recody.recodybackend.movie.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class GenreNotFoundException extends ApplicationException {
    
    public GenreNotFoundException() {
        super(MovieErrorType.GenreNotFound, HttpStatus.NOT_FOUND, "영화 장르를 찾지 못했습니다.");
    }
}
