package com.recody.recodybackend.genre;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.exceptions.CatalogErrorType;
import lombok.Getter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Getter
public class CustomGenreName {
    
    private static final int MAX_GENRE_NAME_LENGTH = 10;
    @JsonProperty( "genreName" )
    private String value;
    
    private CustomGenreName(String value) {
        validateName( value );
        this.value = value;
    }
    
    public static CustomGenreName of(String value) {
        return new CustomGenreName( value );
    }
    
    public static void validateName(String value) {
        if ( ObjectUtils.isEmpty( value ) || !StringUtils.hasText( value ) ) {
            throw ApplicationExceptions.badRequestOf( CatalogErrorType.GenreNameCannotBeNull );
        }
        if ( value.length() > MAX_GENRE_NAME_LENGTH ) {
            throw ApplicationExceptions.badRequestOf( CatalogErrorType.InvalidGenreName );
        }
    }
    
    @Override
    public String toString() {
        return "{\"CustomGenreName\":{"
               + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
               + "}}";
    }
}
