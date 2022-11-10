package com.recody.recodybackend.category;

import com.recody.recodybackend.exceptions.CustomCategoryException;
import lombok.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Value(staticConstructor = "of")
public class CategoryName {
    
    public static final int MAX_CUSTOM_CATEGORY_LENGTH = 10;
    String name;
    
    public CategoryName(String name) {
        requireNonNull(name);
        String trimmedName = preprocessCategoryName(name);
        validateName(trimmedName);
        this.name = trimmedName;
    }
    
    private static String preprocessCategoryName(String name) {
        return name.trim();
    }
    
    private static void validateName(String name) {
        if ( !StringUtils.hasText(name) )
            throw new CustomCategoryException( CustomCategory.CustomCategoryErrorType.CannotHaveEmptyStringName);
        if ( name.length() > MAX_CUSTOM_CATEGORY_LENGTH){
            throw new CustomCategoryException(CustomCategory.CustomCategoryErrorType.CannotOverLengthOf10);
        }
    }
    
    private static void requireNonNull(String name) {
        if ( ObjectUtils.isEmpty(name) )
            throw new CustomCategoryException( CustomCategory.CustomCategoryErrorType.CannotHaveNullName);
    }
    
    @Override
    public String toString() {
        return "{\"CategoryName\":{"
               + "\"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + "}}";
    }
}
