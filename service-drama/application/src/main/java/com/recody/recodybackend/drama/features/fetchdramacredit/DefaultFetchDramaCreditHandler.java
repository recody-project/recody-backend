package com.recody.recodybackend.drama.features.fetchdramacredit;

import com.recody.recodybackend.drama.tmdb.TMDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultFetchDramaCreditHandler implements FetchDramaCreditHandler<TMDBDramaCreditResponse> {
    
    public static final String CREDITS = "/credits";
    public static final String PATH = "/tv/";
    private final WebClient webClient = WebClient.builder().baseUrl( TMDB.TMDB_BASE_URI ).build();
    @Value( "${movie.tmdb.api-key}" )
    private String apiKey;
    
    @Override
    public TMDBDramaCreditResponse handle(FetchDramaCredit query) {
        return webClient.get()
                        .uri( uriBuilder -> uriBuilder.path( PATH + query.getTmdbId() + CREDITS )
                                                      .queryParam( "api_key", apiKey )
                                                      .queryParam( "language",
                                                                   query.getLocale().getLanguage() )
                                                      .build() )
                        .retrieve()
                        .bodyToMono( TMDBDramaCreditResponse.class )
                        .block();
    }
}
