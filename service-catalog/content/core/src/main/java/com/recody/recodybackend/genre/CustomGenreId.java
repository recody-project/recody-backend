package com.recody.recodybackend.genre;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.exceptions.CatalogErrorType;
import lombok.Getter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Getter
public class CustomGenreId{
    
    private final String value;
    
    private CustomGenreId(String value) {
        requireNonNull( value );
        requireCustomGenrePrefix( value );
        this.value = value;
    }
    
    public static CustomGenreId of(String value) {
        return new CustomGenreId( value );
    }
    
    private static void requireNonNull(String value) {
        if ( ObjectUtils.isEmpty( value ) || !StringUtils.hasText( value ) ) {
            throw ApplicationExceptions.badRequestOf( CatalogErrorType.GenreIdCannotBeNull );
        }
    }
    
    private static void requireCustomGenrePrefix(String value) {
        if ( !value.startsWith( Recody.CUSTOM_GENRE_PREFIX ) ) {
            throw ApplicationExceptions.badRequestOf( CatalogErrorType.InvalidGenreId );
        }
    }
    
    @Override
    public String toString() {
        return "{\"CustomGenreId\":{"
               + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
               + "}}";
    }
}
