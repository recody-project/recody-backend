package com.recody.recodybackend.common.contents;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recody.recodybackend.common.contents.exceptions.ContentErrorType;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
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
            throw ApplicationExceptions.badRequestOf( ContentErrorType.GenreIdCannotBeNull );
        }
    }
    
    @Override
    public String toString() {
        return "{\"GenreId\":{"
               + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
               + "}}";
    }
}
