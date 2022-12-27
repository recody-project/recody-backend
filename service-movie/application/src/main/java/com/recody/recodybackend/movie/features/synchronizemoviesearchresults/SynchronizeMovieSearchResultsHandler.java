package com.recody.recodybackend.movie.features.synchronizemoviesearchresults;

import com.recody.recodybackend.movie.search.MovieSearchKeyword;

/**
 * 검색 결과를 한국어, 영어로 요청하여 DB 에 저장한다.
 * @param <R> 결과 타입
 * @author motive
 */
public interface SynchronizeMovieSearchResultsHandler<R> {
    
    R handle(MovieSearchKeyword keyword);
    
    
}
