package com.recody.recodybackend.category;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.exceptions.ErrorType;
import com.recody.recodybackend.exceptions.CustomCategoryException;
import lombok.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Builder
public class CustomCategory implements Category {
    
    public static final int MAX_CUSTOM_CATEGORIES = 5;
    public static final int MAX_CUSTOM_CATEGORY_LENGTH = 10;
    
    @NonNull
    private String id;
    @NonNull
    @JsonUnwrapped
    private CategoryName name;
    @JsonUnwrapped
    private CategoryIconUrl iconUrl;
    @Builder.Default
    private boolean basic = false;
    
    // TODO: id를 CustomCategoryId 로 대체
    public CustomCategory(@NonNull String id, @NonNull CategoryName categoryName, CategoryIconUrl categoryIconUrl, boolean basic) {
        validateId(id);
        this.id = id;
        this.name = categoryName;
        this.iconUrl = categoryIconUrl;
    }
    
    public static void validateId(String id) {
        if ( ObjectUtils.isEmpty(id) )
            throw new CustomCategoryException(CustomCategoryErrorType.CannotHaveNullId);
        if ( !StringUtils.hasText(id) )
            throw new CustomCategoryException(CustomCategoryErrorType.CannotHaveEmptyStringId);
        if ( BasicCategory.isBasic(id) )
            throw new CustomCategoryException(CustomCategoryErrorType.CannotHaveBasicCategoryId);
    }
    
    public String getIconUrl() {
        return iconUrl.getIconUrl();
    }
    
    public String getName() {
        return name.getName();
    }
    
    @Override
    public String toString() {
        return "{\"CustomCategory\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + ", \"basic\":" + basic + "}}";
    }
    
    
    public enum CustomCategoryErrorType implements ErrorType {
        
        InvalidName("category.custom.name.invalid"),
        CannotHaveNullName("category.custom.name.not-null"),
        CannotHaveEmptyStringName("category.custom.name.not-empty"),
        CannotHaveNullId("category.custom.id.not-null"),
        CannotHaveEmptyStringId("category.custom.id.not-empty"),
        CannotHaveBasicCategoryId("category.custom.id.not-basic-id"),
        CustomCategoryAlreadyExists("category.custom.already-exist"),
        CannotOver5CustomCategories("category.custom.count.over5"),
        CannotOverLengthOf10("category.custom.length.over10"),
        
        ;
        
        private final String errorCode;
        
        CustomCategoryErrorType(String errorCode) {
            this.errorCode = errorCode;
        }
        
        @Override
        public String getErrorCode() {
            return this.errorCode;
        }
    }
}
