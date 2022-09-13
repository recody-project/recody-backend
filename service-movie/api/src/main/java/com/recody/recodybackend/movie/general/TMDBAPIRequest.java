package com.recody.recodybackend.movie.general;

import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.common.openapi.AbstractAPIRequest;

/*
* TMDB 의 uri, 요청 파라미터의 name 값 등이 세팅된 요청 객체.
* 이 객체를 delegate 로 사용하면 APIRequester 로 요청을 보낼 수 있다. */
public class TMDBAPIRequest extends AbstractAPIRequest implements APIRequest {
    
    private static final String TMDB_BASE_URI = "https://api.themoviedb.org/3";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    private static final String TMDB_API_KEY_PARAM_NAME = "api_key";
    
    
    public TMDBAPIRequest(String path, String language) {
        super(TMDB_BASE_URI, path, TMDB_API_KEY_PARAM_NAME);
        // 언어는 지정했을 때만 설정한다.
        addRequestParam(TMDB_LANGUAGE_PARAM_NAME, language, false);
    }
    
    /*
    * 하위 상속체에서는 path 만 세팅하여 사용할 수 있다. */
    public TMDBAPIRequest(String path) {
        super(TMDB_BASE_URI, path, TMDB_API_KEY_PARAM_NAME);
    }
    
    @Override
    public String toString() {
        return "[{\"TMDBAPIRequest\":{" + "\"path\":" + ((path != null) ? ("\"" + path + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
