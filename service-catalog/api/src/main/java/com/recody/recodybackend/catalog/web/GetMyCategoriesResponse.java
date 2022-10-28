package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.common.contents.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMyCategoriesResponse {
    
    private List<Category> categories;
    
}
