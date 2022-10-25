package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.common.contents.Content;

public interface CatalogContent extends Content<String>{
    
    String getGlobalContentId();
    String getContentGroupId();

}
