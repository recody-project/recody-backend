package com.recody.recodybackend.drama.features.fetchdramagenres;

import com.recody.recodybackend.drama.tmdb.TMDB;
import com.recody.recodybackend.drama.tmdb.genre.TMDBDramaGenres;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultFetchDramaGenresHandler implements FetchDramaGenresHandler<TMDBDramaGenres>{
    
    public static final String PATH = "/genre/tv/list";
    private final WebClient webClient = WebClient.builder().baseUrl( TMDB.TMDB_BASE_URI ).build();
    @Value( "${movie.tmdb.api-key}" )
    private String apiKey;
    
    
    @Override
    public TMDBDramaGenres handle(FetchDramaGenres query) {
        return webClient.get()
                         .uri( uriBuilder -> uriBuilder.path( PATH )
                                                       .queryParam( "api_key", apiKey )
                                                       .queryParam( "language", query.getLocale().getLanguage())
                                                       .build() )
                         .retrieve()
                         .bodyToMono( TMDBDramaGenres.class )
                         .block();
    }
}
