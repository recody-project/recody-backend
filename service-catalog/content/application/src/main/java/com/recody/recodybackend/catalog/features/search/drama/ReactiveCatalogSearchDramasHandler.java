package com.recody.recodybackend.catalog.features.search.drama;

import com.recody.recodybackend.drama.Dramas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
class ReactiveCatalogSearchDramasHandler implements CatalogSearchDramasHandler<Mono<Dramas>> {
    
    public static final String DRAMA_SEARCH_API_PATH = "/api/v1/drama/search-data";
    public static final String KEYWORD = "keyword";
    private final WebClient dramaWebClient;
    
    public ReactiveCatalogSearchDramasHandler(
            @Qualifier( "DramaServiceWebClient" ) WebClient dramaWebClient) {
        this.dramaWebClient = dramaWebClient;
    }
    
    @Override
    public Mono<Dramas> handle(SearchDramas command) {
        return dramaWebClient.get()
                             .uri( uriBuilder -> uriBuilder.path( DRAMA_SEARCH_API_PATH )
                                                           .queryParam( KEYWORD, command.getKeyword() )
                                                           .queryParam( "genreId", command.getGenreIds() )
                                                           .queryParam( "page", command.getPage() )
                                                           .build() )
                             .retrieve()
                             .bodyToMono( Dramas.class );
    }
}
