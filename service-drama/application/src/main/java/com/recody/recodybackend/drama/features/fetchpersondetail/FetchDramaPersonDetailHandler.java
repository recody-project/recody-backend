package com.recody.recodybackend.drama.features.fetchpersondetail;

/**
 * TMDB 에서 사람 이름을 가져온다.
 * 한글 이름이 있을 경우 한글이름을, 없을 경우 기본 이름을 가져온다.
 * @param <T>
 * @author motive
 */
public interface FetchDramaPersonDetailHandler<T> {
    T handle(FetchDramaPersonDetail command);
    
    
}
