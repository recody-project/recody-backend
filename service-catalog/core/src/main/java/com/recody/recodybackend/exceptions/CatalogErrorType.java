package com.recody.recodybackend.exceptions;

import com.recody.recodybackend.common.exceptions.ErrorType;

public enum CatalogErrorType implements ErrorType {
    UnsupportedCategory("UnsupportedCategory"),
    InvalidCustomCategory("InvalidCustomCategory"),
    CategoryNotFound("CategoryNotFound"),
    ContentNotFound("ContentNotFound"),
    ContentIdCannotBeEmpty("ContentIdCannotBeEmpty"),
    MalformedContentId("MalformedContentId"),
    InvalidRatingScore("InvalidRatingScore"),
    
    IconUrlCannotBeNull("category.icon-url.not-null"),
    RatingScoreCannotBeNull("category.rating.not-null"),
    ;
    
    private final String errorCode;
    
    CatalogErrorType(String errorCode) { this.errorCode = errorCode; }
    
    @Override
    public String getErrorCode() { return this.errorCode; }
}
