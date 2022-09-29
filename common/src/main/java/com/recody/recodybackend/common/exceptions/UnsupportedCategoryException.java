package com.recody.recodybackend.common.exceptions;

import org.springframework.http.HttpStatus;

public class UnsupportedCategoryException extends ApplicationException{
    
    public UnsupportedCategoryException() {
        super(GlobalErrorType.UnsupportedCategory, HttpStatus.NOT_FOUND, "지원하지 않는 카테고리입니다.");
    }
}
