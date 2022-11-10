package com.recody.recodybackend.catalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class CatalogApplicationConfig {
    @Value("${catalog.movie.search.base-url}")
    private String baseUrl;
    
    @Value("${catalog.movie.access-token}")
    private String bearerToken;
    
    
    @Bean("MovieServiceWebClient")
    WebClient webClient(){
        return WebClient.builder()
                       .baseUrl( baseUrl )
                       .defaultHeader( "Authorization", "Bearer " + bearerToken )
                        .build();
    }
}
