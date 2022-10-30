package com.recody.recodybackend.catalog.features.category.delete;

import com.recody.recodybackend.common.events.CategoryDeleted;

public interface DeleteCategoryHandler {
    
    CategoryDeleted handle(DeleteCategory command);
    
}
