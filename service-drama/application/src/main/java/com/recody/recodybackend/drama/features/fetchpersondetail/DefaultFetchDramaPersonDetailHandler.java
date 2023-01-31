package com.recody.recodybackend.drama.features.fetchpersondetail;

import com.recody.recodybackend.drama.tmdb.TMDB;
import com.recody.recodybackend.drama.tmdb.TMDBGetPersonDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * TMDB 에서 인물 정보를 받아 그대로 반환하는 핸들러.
 * @author motive
 */
@Component("DefaultFetchDramaPersonDetailHandler")
@RequiredArgsConstructor
@Slf4j
class DefaultFetchDramaPersonDetailHandler implements FetchDramaPersonDetailHandler<TMDBGetPersonDetailResponse>{
    
    private static final String path = "/person/";
    private final WebClient webClient = WebClient.builder().baseUrl( TMDB.TMDB_BASE_URI ).build();
    
    @Value( "${movie.tmdb.api-key}" )
    private String apiKey;
    
    @Override
    public TMDBGetPersonDetailResponse handle(FetchDramaPersonDetail command) {
        log.debug( "handling command: {}", command );
        Integer tmdbPersonId = command.getTmdbPersonId();
        return webClient.get()
                        .uri( uriBuilder -> uriBuilder.path( path + tmdbPersonId )
                                                      .queryParam( "api_key", apiKey ).build() )
                        .retrieve()
                        .bodyToMono( TMDBGetPersonDetailResponse.class )
                        .block();
    }
}
