package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.event.CategoryPersonalized;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCategoryOnContentResponse {
    private CategoryPersonalized event;
    
    public ChangeCategoryOnContentResponse(CategoryPersonalized event) {
        this.event = event;
    }
    
    @Override
    public String toString() {
        return "{\"ChangeCategoryOnContentResponse\":{"
               + "\"event\":" + event
               + "}}";
    }
}
