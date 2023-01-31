package com.recody.recodybackend.catalog.config;

import com.recody.recodybackend.common.Recody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
class CatalogApplicationConfig {
    @Value("${catalog.movie.search.base-url}")
    private String baseUrl;
    
    @Value( "${catalog.users.base-url}" )
    public String usersBaseUrl;
    
    @Value("${catalog.movie.access-token}")
    private String bearerToken;
    
    @Bean(value = Recody.CATALOG_TASK_EXECUTOR)
    Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("catalog-task-");
        executor.initialize();
        return executor;
    }
    
    
    @Bean("MovieServiceWebClient")
    WebClient movieWebClient(){
        return WebClient.builder()
                       .baseUrl( baseUrl )
                       .defaultHeader( "Authorization", "Bearer " + bearerToken )
                        .build();
    }
    
    @Bean("DramaServiceWebClient")
    WebClient dramaWebClient(){
        return WebClient.builder()
                        .baseUrl( baseUrl )
                        .defaultHeader( "Authorization", "Bearer " + bearerToken )
                        .build();
    }
    
    @Bean
    WebClient webClient(){
        return WebClient.builder()
                        .baseUrl( usersBaseUrl )
                        .defaultHeader( "Authorization", "Bearer " + bearerToken )
                        .build();
    }
}
