package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.movie.Movies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
class ReactiveCatalogSearchMoviesHandler implements CatalogSearchMoviesHandler<Mono<Movies>> {
    
    private final WebClient movieWebClient;
    private static final String path = "/api/v3/movie/search-data";
    private static final String MOVIE_SEARCH_PARAM_NAME = "movieName";
    private static final String LANGUAGE_PARAM_NAME = "language";
    private static final String GENRE_IDS_PARAM_NAME = "genreIds";
    
    public ReactiveCatalogSearchMoviesHandler(
            @Qualifier( "MovieServiceWebClient" ) WebClient movieWebClient) {
        this.movieWebClient = movieWebClient;
    }
    
    @Override
    public Mono<Movies> handle(SearchMovies command) {
        return movieWebClient.get()
                      .uri( uriBuilder -> uriBuilder.path( path )
                                                    .queryParam( MOVIE_SEARCH_PARAM_NAME, command.getKeyword() )
                                                    .queryParam( LANGUAGE_PARAM_NAME, command.getLanguage() )
                                                    .queryParam( GENRE_IDS_PARAM_NAME, command.getGenreIds() )
                                                    .build() )
                      .retrieve()
                      .bodyToMono( Movies.class );
    }
}
