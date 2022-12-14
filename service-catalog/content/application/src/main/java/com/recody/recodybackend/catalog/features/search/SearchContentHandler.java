package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.catalog.web.SearchContentResponse;
import com.recody.recodybackend.catalog.web.SearchContentWithFiltersResponse;

/**
 * 작품을 검색한다.
 * 유저가 설정한 수정된 작품정보를 반영하여 결과를 반환한다.*/
public interface SearchContentHandler {
    
    SearchContentResponse handle(SearchContent command);
    
    SearchContentWithFiltersResponse handle(SearchContentWithFilters command);
}
