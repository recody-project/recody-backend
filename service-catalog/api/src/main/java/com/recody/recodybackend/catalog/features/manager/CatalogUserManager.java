package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.CatalogUser;

public interface CatalogUserManager {
    
    CatalogUser load(Long userId);
    
}
