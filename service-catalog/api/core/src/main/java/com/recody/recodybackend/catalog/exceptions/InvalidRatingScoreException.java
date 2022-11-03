package com.recody.recodybackend.catalog.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidRatingScoreException extends ApplicationException {
    
    public InvalidRatingScoreException() {
        super(CatalogErrorType.InvalidRatingScore, HttpStatus.BAD_REQUEST, "점수는 1에서 10까지만 가능합니다.");
    }
}
