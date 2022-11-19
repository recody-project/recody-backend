package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.CatalogUser;
import com.recody.recodybackend.common.contents.Category;

import java.util.List;

public interface CategoryManager {
    
    Category load(String categoryId, CatalogUser user);
    
    List<Category> loadAll(CatalogUser user);
    
}
