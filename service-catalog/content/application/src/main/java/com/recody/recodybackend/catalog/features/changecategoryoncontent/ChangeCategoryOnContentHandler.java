package com.recody.recodybackend.catalog.features.changecategoryoncontent;

import com.recody.recodybackend.event.CategoryPersonalized;

public interface ChangeCategoryOnContentHandler {
    
    CategoryPersonalized handle(ChangeCategoryOnContent command);
    
}
