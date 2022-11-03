package com.recody.recodybackend.catalog.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends ApplicationException {
    
    public CategoryNotFoundException() {
        super(CatalogErrorType.CategoryNotFound, HttpStatus.NOT_FOUND, "해당 카테고리가 존재하지 않습니다.");
    }
}
