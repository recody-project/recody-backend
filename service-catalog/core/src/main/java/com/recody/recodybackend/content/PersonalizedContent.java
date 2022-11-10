package com.recody.recodybackend.content;

import com.recody.recodybackend.common.contents.Content;

public interface PersonalizedContent extends Content<String> {
    
    Long getPersonalizedUserId();
    
}
