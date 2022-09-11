package com.recody.recodybackend.movie.features.tmdb;

import com.recody.recodybackend.common.openapi.AbstractAPIRequester;
import com.recody.recodybackend.common.openapi.ApiRequester;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
* TMDB 에 요청을 할 수 있도록 api key 를 받아 Request 객체에 세팅 하는 API Requester */
@Component
@Slf4j
public class TMDBAPIRequester
        extends AbstractAPIRequester<TMDBAPIRequest>
        implements ApiRequester<TMDBAPIRequest> {
    
    public TMDBAPIRequester(@Value("${movie.tmdb.api-key}") String apikey) {
        super(apikey);
    }
}
