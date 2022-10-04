package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.common.contents.Category;

public interface CatalogContent {
    
    String getContentId();
    Category getCategory();
    String getTitle();
    String getImageUrl();
}
