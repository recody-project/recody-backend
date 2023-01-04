package com.recody.recodybackend.catalog.features.category.modify;


import com.recody.recodybackend.category.CustomCategory;

public interface ModifyCategoryHandler {
    
    CustomCategory handle(ModifyCategory command);
    
}
