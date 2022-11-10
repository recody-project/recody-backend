package com.recody.recodybackend.catalog.features.changecategoryoncontent;

import com.recody.recodybackend.common.events.CategoryPersonalized;

public interface ChangeCategoryOnContentHandler {
    
    CategoryPersonalized handle(ChangeCategoryOnContent command);
    
}
