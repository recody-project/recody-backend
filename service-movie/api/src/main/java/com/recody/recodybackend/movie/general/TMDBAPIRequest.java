package com.recody.recodybackend.movie.general;

import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.common.openapi.AbstractAPIRequest;

/*
 * TMDB 의 uri, 요청 파라미터의 name 값 등이 세팅된 요청 객체.
 * 이 객체를 delegate 로 사용하면 APIRequester 로 요청을 보낼 수 있다. */
public class TMDBAPIRequest extends AbstractAPIRequest implements APIRequest {
    
    private static final String TMDB_BASE_URI = "https://api.themoviedb.org/3";
    
    
    public TMDBAPIRequest() {
        super(TMDB_BASE_URI);
    }
    
    @Override
    public String toString() {
        return "[{\"TMDBAPIRequest\":{" + "\"path\":" + ((path != null) ? ("\"" + path + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
