package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.CatalogUser;
import com.recody.recodybackend.common.contents.Category;

import java.util.List;

public interface CategoryManager {
    
    Category load(String categoryId, CatalogUser user);
    
    List<Category> loadAll(CatalogUser user);
    
}
