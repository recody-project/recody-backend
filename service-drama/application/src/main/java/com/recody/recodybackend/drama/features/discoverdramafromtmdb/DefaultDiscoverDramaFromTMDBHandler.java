package com.recody.recodybackend.drama.features.discoverdramafromtmdb;

import com.recody.recodybackend.drama.tmdb.TMDB;
import com.recody.recodybackend.drama.tmdb.TMDBSearchDramaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultDiscoverDramaFromTMDBHandler implements DiscoverDramaFromTMDBHandler<TMDBSearchDramaResponse>{

    private final WebClient webClient = WebClient.builder().baseUrl( TMDB.TMDB_BASE_URI ).build();
    private static final String PATH = "/discover/tv";
    @Value( "${movie.tmdb.api-key}" )
    private String apiKey;

    @Override
    public TMDBSearchDramaResponse handle(DiscoverDramaFromTMDB query) {
        log.debug( "handling query: {}", query );
        return webClient.get()
                .uri( uriBuilder -> {
                    uriBuilder.path( PATH )
                            .queryParam( "api_key", apiKey )
                            .queryParam( "language", query.getLanguage() );
                    if (query.getPage() != null){
                        uriBuilder.queryParam( "page", query.getPage() );
                    }
                    return uriBuilder.build();
                } )
                .retrieve()
                .bodyToMono( TMDBSearchDramaResponse.class )
                .block();    }
}
