package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.features.CatalogUser;
import com.recody.recodybackend.common.contents.Category;

import java.util.List;

public interface CategoryManager extends CategoryRegistrar {
    
    Category load(String categoryId, CatalogUser user);
    
    List<Category> loadAll(CatalogUser user);
    
}
