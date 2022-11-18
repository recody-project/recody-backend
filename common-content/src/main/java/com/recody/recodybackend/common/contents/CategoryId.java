package com.recody.recodybackend.common.contents;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recody.recodybackend.common.contents.exceptions.ContentErrorType;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import lombok.Getter;
import lombok.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Value( staticConstructor = "of" )
@Getter
public class CategoryId {
    
    @JsonProperty( "categoryId" )
    String value;
    
    public CategoryId(String value) {
        validateId( value );
        this.value = value;
    }
    
    public static void validateId(String id) {
        if ( ObjectUtils.isEmpty( id ) )
            throw ApplicationExceptions.badRequestOf( ContentErrorType.CannotHaveNullId );
        if ( !StringUtils.hasText( id ) )
            throw ApplicationExceptions.badRequestOf( ContentErrorType.CannotHaveEmptyStringId );
    }
    
    @Override
    public String toString() {
        return "{\"CategoryId\":{"
               + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
               + "}}";
    }
}
