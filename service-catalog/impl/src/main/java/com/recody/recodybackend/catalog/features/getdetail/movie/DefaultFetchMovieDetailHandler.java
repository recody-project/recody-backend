package com.recody.recodybackend.catalog.features.getdetail.movie;

import com.recody.recodybackend.catalog.features.api.movie.MovieAPIRequest;
import com.recody.recodybackend.common.openapi.APIFeatureFactory;
import com.recody.recodybackend.common.openapi.APIRequester;
import com.recody.recodybackend.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultFetchMovieDetailHandler implements FetchMovieDetailHandler {
    
    private final APIRequester<MovieAPIRequest> requester;
    private final APIFeatureFactory<MovieDetailRequest> featureFactory;
    
    @Override
    public Movie handle(FetchMovieDetail command) {
        MovieDetailRequest request = featureFactory.newFeature(command.getMovieId(), command.getLanguage());
        Movie movie = requester.requestAndGet(request, Movie.class);
    
        log.info("영화 정보를 Movie 서비스에서 가져왔습니다.");
        return movie;
    }
}
