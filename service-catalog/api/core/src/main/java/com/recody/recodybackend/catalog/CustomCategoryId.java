package com.recody.recodybackend.catalog;

import com.recody.recodybackend.catalog.exceptions.CustomCategoryException;
import com.recody.recodybackend.common.contents.BasicCategory;
import lombok.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Value(staticConstructor = "of")
public class CustomCategoryId {
    
    String categoryId;
    
    private CustomCategoryId(String categoryId) {
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
