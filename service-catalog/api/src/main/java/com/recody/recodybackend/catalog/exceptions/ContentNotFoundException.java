package com.recody.recodybackend.catalog.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class ContentNotFoundException extends ApplicationException {
    
    public ContentNotFoundException() {
        super(CatalogErrorType.ContentNotFound, HttpStatus.NOT_FOUND, "작품을 찾을 수 없습니다.");
    }
}
