package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.event.CategoryDeleted;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteCategoryResponse {
    
    private CategoryDeleted event;

}
