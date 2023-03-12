package com.recody.recodybackend.insight;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableFeignClients
class InsightConfiguration {
    
    @Value( "${insight.record.access-token}" )
    private String accessToken;
    
    @Value( "${insight.record.base-url}" )
    private String baseUrl;
    
    
    @Bean( name = "insightWebClient" )
    WebClient insightWebClient() {
        return WebClient.builder()
                        .baseUrl( baseUrl )
                        .defaultHeader( "Authorization", "Bearer " + accessToken )
                        .build();
    }
}
