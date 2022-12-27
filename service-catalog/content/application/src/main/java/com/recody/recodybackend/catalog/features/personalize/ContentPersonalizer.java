package com.recody.recodybackend.catalog.features.personalize;

import com.recody.recodybackend.content.PersonalizedContent;
import com.recody.recodybackend.common.contents.Content;

public interface ContentPersonalizer<T extends Content<?>, P extends PersonalizedContent>{
    
    P personalize(T content, Long userId);
    
}
