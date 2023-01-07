package com.recody.recodybackend.drama.features.getdramadetail;

/**
 * 드라마 상세정보를 가져옵니다.
 * - 기본적인 상세정보는 FetchDramaDetail 을 통해서 가져옵니다.
 * - 배우정보는 FetchDramaCredit 으로 가져옵니다.
 * @author motive
 */
public interface GetDramaDetailHandler<R> {
    
    R handle(GetDramaDetail query);
    
}
