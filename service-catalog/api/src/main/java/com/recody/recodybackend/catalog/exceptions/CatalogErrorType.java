package com.recody.recodybackend.catalog.exceptions;

import com.recody.recodybackend.common.exceptions.ErrorType;

public enum CatalogErrorType implements ErrorType {
    UnsupportedCategory("UnsupportedCategory")
    ;
    
    private final String errorCode;
    
    CatalogErrorType(String errorCode) {
        this.errorCode = errorCode;
    }
    
    @Override
    public String getErrorCode() {
        return null;
    }
}
