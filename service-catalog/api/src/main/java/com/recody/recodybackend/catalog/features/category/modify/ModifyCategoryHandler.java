package com.recody.recodybackend.catalog.features.category.modify;


import com.recody.recodybackend.catalog.CustomCategory;

public interface ModifyCategoryHandler {
    
    CustomCategory handle(ModifyCategory command);
    
}
