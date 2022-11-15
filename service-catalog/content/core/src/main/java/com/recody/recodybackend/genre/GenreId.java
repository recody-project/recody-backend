package com.recody.recodybackend.genre;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.exceptions.CatalogErrorType;
import lombok.Getter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Getter
public class GenreId {
    
    @JsonProperty("genreId")
    private final String value;
    
    protected GenreId(String value) {
        requireNonNull( value );
        this.value = value;
    }
    
    public static GenreId of(String value) {
        return new GenreId( value );
    }
    
    
    private static void requireNonNull(String value) {
        if ( ObjectUtils.isEmpty( value ) || !StringUtils.hasText( value ) ) {
            throw ApplicationExceptions.badRequestOf( CatalogErrorType.GenreIdCannotBeNull );
        }
    }
}
