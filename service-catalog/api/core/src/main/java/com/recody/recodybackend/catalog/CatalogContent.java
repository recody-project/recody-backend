package com.recody.recodybackend.catalog;

import com.recody.recodybackend.common.contents.Content;

public interface CatalogContent extends Content<String>{
    
    String getGlobalContentId();
    String getContentGroupId();

}
