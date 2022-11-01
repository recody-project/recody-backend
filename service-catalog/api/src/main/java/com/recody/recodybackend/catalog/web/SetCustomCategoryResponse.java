package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.features.setcustomcategory.CategoryPersonalized;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetCustomCategoryResponse {
    private CategoryPersonalized event;
    
    public SetCustomCategoryResponse(CategoryPersonalized event) {
        this.event = event;
    }
    
    @Override
    public String toString() {
        return "{\"SetCustomCategoryResponse\":{"
               + "\"event\":" + event
               + "}}";
    }
}
