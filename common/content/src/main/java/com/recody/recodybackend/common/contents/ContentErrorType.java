package com.recody.recodybackend.common.contents;

import com.recody.recodybackend.common.exceptions.ErrorType;

public enum ContentErrorType implements ErrorType {
    GenreIdCannotBeNull("catalog.genre.id.not-null"),
    GenreCustomizationCannotOver5("catalog.genre.personalize.over"),
    ;
    
    private final String errorCode;
    
    ContentErrorType(String errorCode) { this.errorCode = errorCode; }
    
    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
