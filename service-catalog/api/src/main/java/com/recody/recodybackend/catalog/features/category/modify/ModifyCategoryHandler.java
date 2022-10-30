package com.recody.recodybackend.catalog.features.category.modify;

import com.recody.recodybackend.catalog.features.CustomCategory;

public interface ModifyCategoryHandler {
    
    CustomCategory handle(ModifyCategory command);
    
}
