package com.recody.recodybackend.catalog.features.search;

/**
 * 작품을 검색한다.
 * 유저가 설정한 수정된 작품정보를 반영하여 결과를 반환한다.*/
public interface SearchContentHandler {
    
    SearchContentResponse handle(SearchContent command);
}
