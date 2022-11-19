package com.recody.recodybackend.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recody.recodybackend.exceptions.CustomCategoryException;
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
            throw new CustomCategoryException( CustomCategory.CustomCategoryErrorType.CannotHaveNullId );
        if ( !StringUtils.hasText( id ) )
            throw new CustomCategoryException( CustomCategory.CustomCategoryErrorType.CannotHaveEmptyStringId );
    }
    
    @Override
    public String toString() {
        return "{\"CategoryId\":{"
               + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
               + "}}";
    }
}
