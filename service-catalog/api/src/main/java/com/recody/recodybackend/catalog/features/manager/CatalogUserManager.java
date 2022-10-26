package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.features.CatalogUser;

public interface CatalogUserManager {
    
    CatalogUser load(Long userId);
    
}
