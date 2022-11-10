package com.recody.recodybackend.category;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.exceptions.CustomCategoryException;
import lombok.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Value(staticConstructor = "of")
public class CustomCategoryId {
    
    String categoryId;
    
    public CustomCategoryId(String categoryId) {
        validateId( categoryId );
        this.categoryId = categoryId;
    }
    
    public static void validateId(String id) {
        if ( ObjectUtils.isEmpty( id ) )
            throw new CustomCategoryException( CustomCategory.CustomCategoryErrorType.CannotHaveNullId);
        if ( !StringUtils.hasText( id ) )
            throw new CustomCategoryException( CustomCategory.CustomCategoryErrorType.CannotHaveEmptyStringId);
        if ( BasicCategory.isBasic( id ) )
            throw new CustomCategoryException( CustomCategory.CustomCategoryErrorType.CannotHaveBasicCategoryId);
    }
    
    @Override
    public String toString() {
        return "{\"CategoryId\":{"
               + "\"value\":" + ((categoryId != null) ? ("\"" + categoryId + "\"") : null)
               + "}}";
    }
}
