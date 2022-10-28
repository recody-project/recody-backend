package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.features.ContentId;

public interface ContentLoader<Entity> {
    
    CatalogContent load(ContentId contentId);
    
    Entity loadEntity(ContentId contentId);
    
    
    
    
    
}
