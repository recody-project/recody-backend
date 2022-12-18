package com.recody.recodybackend.drama.features.searchdramafromtmdb;

import com.recody.recodybackend.movie.features.tmdb.TMDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSearchDramaFromTMDBHandler implements SearchDramaFromTMDBHandler<TMDBSearchDramaResponse> {
    
    private final WebClient webClient = WebClient.builder().baseUrl( TMDB.TMDB_BASE_URI ).build();
    private static final String PATH = "/search/tv";
    @Value( "${movie.tmdb.api-key}" )
    private String apiKey;
    
    @Override
    public TMDBSearchDramaResponse handle(SearchDramaFromTMDB query) {
        log.debug( "handling query: {}", query );
        return webClient.get()
                        .uri( uriBuilder -> uriBuilder.path( PATH )
                                                      .queryParam( "api_key", apiKey )
                                                      .queryParam( "query", query.getQueryText() )
                                                      .queryParam( "language", query.getLanguage() )
                                                      .queryParam( "page", query.getPage() )
                                                      .build() )
                        .retrieve()
                        .bodyToMono( TMDBSearchDramaResponse.class )
                        .block();
    }
}
