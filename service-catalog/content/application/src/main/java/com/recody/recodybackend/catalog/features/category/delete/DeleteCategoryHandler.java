package com.recody.recodybackend.catalog.features.category.delete;

import com.recody.recodybackend.event.CategoryDeleted;

public interface DeleteCategoryHandler {
    
    CategoryDeleted handle(DeleteCategory command);
    
}
