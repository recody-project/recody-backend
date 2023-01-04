package com.recody.recodybackend.catalog.features.wish.add;

import com.recody.recodybackend.exceptions.ContentNotFoundException;

import java.util.UUID;

/**
 * 특정 작품에 위시 표시를 한다. 작품은 특정 유저의 위시리스트에 추가된다. */
public interface AddToWishlistHandler {
    
    /**
     * @throws ContentNotFoundException : 위시 추가는 존재하는 컨텐츠에만 가능하다.
//     * @throws UserNotFoundException : 존재하지 않는 유저일때.
     * */
    UUID handle(AddToWishlist command);
}
