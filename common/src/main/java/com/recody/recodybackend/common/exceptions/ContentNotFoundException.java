package com.recody.recodybackend.common.exceptions;

import org.springframework.http.HttpStatus;

public class ContentNotFoundException extends ApplicationException{
    
    public ContentNotFoundException() {
        super(GlobalErrorType.ContentNotFound, HttpStatus.NOT_FOUND, "존재하지 않는 작품입니다.");
    }
}
