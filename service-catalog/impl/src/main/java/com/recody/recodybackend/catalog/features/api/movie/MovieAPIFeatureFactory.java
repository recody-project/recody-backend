package com.recody.recodybackend.catalog.features.api.movie;

import com.recody.recodybackend.catalog.features.getdetail.movie.MovieDetailRequest;
import com.recody.recodybackend.common.openapi.APIFeatureFactory;
import com.recody.recodybackend.common.openapi.APIRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
class MovieAPIFeatureFactory implements APIFeatureFactory<MovieDetailRequest> {
    
    private final APIRequestFactory<MovieAPIRequest> requestFactory;
    
    @Override
    public MovieDetailRequest newFeature(Object... args) {
        log.debug("MovieDetailRequest args: {}", Arrays.toString(args));
        MovieAPIRequest movieAPIRequest = requestFactory.newRequest();
        MovieDetailRequest feature = new MovieDetailRequest(movieAPIRequest, (String) args[0], (String) args[1]);
        return feature;
    }
}
