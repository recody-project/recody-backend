package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.features.CatalogUser;
import com.recody.recodybackend.catalog.features.CustomCategory;
import com.recody.recodybackend.catalog.features.category.CategoryIconUrl;
import com.recody.recodybackend.catalog.features.category.CategoryName;

public interface CategoryRegistrar {
    
    CustomCategory register(CategoryName name, CategoryIconUrl iconUrl, CatalogUser user);
}
