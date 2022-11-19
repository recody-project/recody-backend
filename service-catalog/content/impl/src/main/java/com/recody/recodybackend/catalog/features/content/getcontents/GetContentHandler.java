package com.recody.recodybackend.catalog.features.content.getcontents;

import com.recody.recodybackend.common.contents.Content;

public interface GetContentHandler {
    
    
    /**
     * 하나의 작품을 가져온다.
     * 작품은 개인화된 정보이다.
     * 개인화될 정보는
     * @param command : contentId
     * @return :
     */
    Content<?> handle(GetContent command);
    
}
