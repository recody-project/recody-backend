package com.recody.recodybackend.movie.features.fetchpersonname;

import com.recody.recodybackend.movie.TMDBPersonName;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
class DefaultFetchPersonNameHandler implements FetchPersonNameHandler<TMDBPersonName> {
    
    private static final String path = "/person/";
    private final WebClient webClient;
    
    public DefaultFetchPersonNameHandler(@Qualifier( TMDB.TMDB_WEB_CLIENT ) WebClient webClient) {
        this.webClient = webClient;
    }
    
    @Value( "${movie.tmdb.api-key}" )
    private String apiKey;
    
    @Override
    public TMDBPersonName handle(FetchPersonName command) {
        log.debug( "handling command: {}", command );
        Integer tmdbPersonId = command.getTmdbPersonId();
        return webClient.get()
                        .uri( uriBuilder -> uriBuilder.path( path + tmdbPersonId )
                                                      .queryParam( "api_key", apiKey ).build() )
                        .retrieve()
                        .bodyToMono( TMDBGetPersonDetailResponse.class )
                        .map( response -> {
                            log.debug( "response: {}", response );
                            List<String> alsoKnownAss = response.getAlsoKnownAs();
                            if ( Objects.isNull( alsoKnownAss ) || alsoKnownAss.isEmpty() ) {
                                return TMDBPersonName.of( response.getName() );
                            }
                            return TMDBPersonName.firstKoreanNameOf( alsoKnownAss );
                        } ).block();
    }
}
