package com.recody.recodybackend.catalog.features.record.resolvecategory;

import com.recody.recodybackend.common.contents.Category;

public interface CategoryResolver {
    
    Category resolve(String id);
    
}
