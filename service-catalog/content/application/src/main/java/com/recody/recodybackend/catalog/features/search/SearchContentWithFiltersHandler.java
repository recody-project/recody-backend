package com.recody.recodybackend.catalog.features.search;

/**
 * 필터링을 적용하여 검색결과를 반환한다.
 * 1. Catalog 캐시를 사용할 경우.
 * - 검색 결과는 Catalog DB 를 사용한다.
 * - 페이징 정보도 Catalog 를 기준으로 반환한다.
 * 2. 각 컨텐츠 서비스의 검색기능을 사용할 경우.
 * - 검색 결과는 각 서비스의 API 를 통해 DB 를 사용한다.
 * - 페이징 정보는 Catalog 를 기준으로 계산한다.
 * @author motive
 */
public interface SearchContentWithFiltersHandler<R> {
    
    R handle(SearchContentWithFilters command);
    
}
