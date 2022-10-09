package com.recody.recodybackend.catalog.features.wish.get;


import com.recody.recodybackend.common.contents.Content;

import java.util.List;

/**
 * 사용자의 위시리스트 작품들을 가져온다.
 * 위시 처리된 작품들의 contentId 들을 반환한다.
 * */
public interface GetMyWishlistHandler {
    
    List<Content> handle(GetMyWishlist command);
    
    
    
}
