package com.recody.recodybackend.movie.features.searchmoviesfromtmdb;

import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchResponse;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
class DefaultSearchMoviesFromTMDBHandler implements SearchMoviesFromTMDBHandler<TMDBMovieSearchResponse> {
    
    private static final String TMDB_MOVIE_SEARCH_PATH = "/search/movie";
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    public static final String PAGE_PARAM_NAME = "page";
    public static final String API_KEY_PARAM_NAME = "api_key";
    private final WebClient webClient;
    
    @Value( "${movie.tmdb.api-key}" )
    private String apiKey;
    
    public DefaultSearchMoviesFromTMDBHandler(@Qualifier( TMDB.TMDB_WEB_CLIENT ) WebClient webClient) {
        this.webClient = webClient;
    }
    
    @Override
    public TMDBMovieSearchResponse handle(SearchMoviesFromTMDB command) {
        log.debug( "handling command: {}", command );
        return webClient.get()
                        .uri( uriBuilder ->
                                      uriBuilder.path( TMDB_MOVIE_SEARCH_PATH )
                                                .queryParam( API_KEY_PARAM_NAME, apiKey )
                                                .queryParam( TMDB_LANGUAGE_PARAM_NAME, command.getLanguage() )
                                                .queryParam( TMDB_QUERY_PARAM_NAME, command.getMovieName() )
                                                .queryParam( PAGE_PARAM_NAME, command.getPage() )
                                                .build()
                            )
                        .retrieve()
                        .bodyToMono( TMDBMovieSearchResponse.class )
                        .block();
    }
}
