package com.recody.recodybackend.common.contents.exceptions;

import com.recody.recodybackend.common.exceptions.ErrorType;

public enum ContentErrorType implements ErrorType {
    GenreIdCannotBeNull("catalog.genre.id.not-null"),
    GenreCustomizationCannotOver5("catalog.genre.personalize.over"),
    CannotHaveEmptyStringName("catalog.category.custom.name.not-empty"),
    CannotHaveNullId("catalog.category.custom.id.not-null"),
    CannotHaveEmptyStringId("catalog.category.custom.id.not-empty"),
    ContentIdCannotBeEmpty("ContentIdCannotBeEmpty"),
    MalformedContentId("MalformedContentId"),
    
    
    ;
    
    private final String errorCode;
    
    ContentErrorType(String errorCode) { this.errorCode = errorCode; }
    
    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
