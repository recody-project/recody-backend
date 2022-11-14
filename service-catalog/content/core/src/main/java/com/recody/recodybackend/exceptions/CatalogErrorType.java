package com.recody.recodybackend.exceptions;

import com.recody.recodybackend.common.exceptions.ErrorType;

public enum CatalogErrorType implements ErrorType {
    UnsupportedCategory("UnsupportedCategory"),
    InvalidCustomCategory("InvalidCustomCategory"),
    CategoryNotFound("CategoryNotFound"),
    ContentNotFound("ContentNotFound"),
    GenreIdCannotBeNull("catalog.genre.id.not-null"),
    GenreNameCannotBeNull("catalog.genre.name.not-null"),
    GenreNameAlreadyExists("catalog.genre.name.already-exists"),
    InvalidGenreId("catalog.genre.id.invalid"),
    InvalidGenreName("catalog.genre.name.invalid"),
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
