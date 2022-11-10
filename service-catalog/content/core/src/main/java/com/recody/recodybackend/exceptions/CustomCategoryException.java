package com.recody.recodybackend.exceptions;

import com.recody.recodybackend.category.CustomCategory;
import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class CustomCategoryException extends ApplicationException {
    
    
    public CustomCategoryException() {
        super(CatalogErrorType.InvalidCustomCategory, HttpStatus.BAD_REQUEST, "부적절한 커스텀 카테고리 입니다.");
    }
    
    public CustomCategoryException(CustomCategory.CustomCategoryErrorType specific) {
        super(specific, HttpStatus.BAD_REQUEST, "부적절한 커스텀 카테고리 입니다.");
    }
}
