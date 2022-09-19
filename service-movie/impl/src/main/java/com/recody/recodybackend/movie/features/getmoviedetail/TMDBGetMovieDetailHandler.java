package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.common.openapi.APIRequester;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class TMDBGetMovieDetailHandler implements GetMovieDetailHandler {
    
    private final APIRequester<TMDBAPIRequest> requester;
    
    @Override
    public GetMovieDetailResult handle(GetMovieDetail request) {
        TMDBMovieDetail tmdbMovieDetail = requester.requestAndGet(request, TMDBMovieDetail.class);
        tmdbMovieDetail.setPosterPath(TMDB.fullPosterPath(tmdbMovieDetail.getPosterPath()));
        return GetMovieDetailResult.builder()
                       .requestInfo(request)
                       .detail(tmdbMovieDetail)
                                   .build();
    }
}
