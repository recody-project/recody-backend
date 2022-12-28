package com.recody.recodybackend.catalog.features.fetchmoviedetailv2;

import com.recody.recodybackend.movie.MovieDetailViewModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
class DefaultFetchMovieDetailHandlerV2 implements FetchMovieDetailHandlerV2<MovieDetailViewModel>{
    @Getter
    private static final String path = "/api/v3/movie/detail-data";
    @Getter
    private static final String MOVIE_ID ="movieId";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    private final WebClient movieWebClient;
    
    public DefaultFetchMovieDetailHandlerV2(@Qualifier("MovieServiceWebClient") WebClient movieWebClient) {
        this.movieWebClient = movieWebClient;
    }
    
    @Override
    public MovieDetailViewModel handle(FetchMovieDetailV2 query) {
        return movieWebClient.get()
                             .uri( uriBuilder -> uriBuilder.path( path )
                                                           .queryParam( MOVIE_ID, query.getMovieId() )
                                                           .build() )
                             .header( ACCEPT_LANGUAGE, query.getLocale().getLanguage() )
                             .retrieve()
                             .bodyToMono( MovieDetailViewModel.class )
                             .block();
    }
}
