package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith( MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class ReactiveCatalogSearchTMDBMoviesHandlerTest {
    
    @Test
    @DisplayName( "webClient 사용 기본 테스트" )
    void simpleWebClientTest() {
        // given
        
        
        // when
        
        // then
    }
    
    
    
}