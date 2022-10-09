package com.recody.recodybackend.record.features.resolvecategory;

import com.recody.recodybackend.common.contents.Category;

public interface CategoryResolver {
    
    Category resolve(String id);
    
}
