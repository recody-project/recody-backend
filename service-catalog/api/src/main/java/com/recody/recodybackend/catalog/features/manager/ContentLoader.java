package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.CatalogContent;
import com.recody.recodybackend.catalog.ContentId;

import java.util.Locale;

public interface ContentLoader<Entity> {
    
    CatalogContent load(ContentId contentId, Locale locale);
    
    Entity loadEntity(ContentId contentId);
    
    
    
    
    
}
