package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.common.contents.Content;

public interface PersonalizedContent extends Content<String> {
    
    Long getPersonalizedUserId();
    
}
