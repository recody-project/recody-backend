package com.recody.recodybackend.catalog.features.category.getmycategories;

import com.recody.recodybackend.common.contents.Category;

import java.util.List;

public interface GetMyCategoriesHandler {
    
    List<Category> handle(GetMyCategories command);
    
}
