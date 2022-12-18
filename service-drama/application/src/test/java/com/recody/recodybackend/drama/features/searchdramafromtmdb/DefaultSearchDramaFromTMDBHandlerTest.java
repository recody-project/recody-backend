package com.recody.recodybackend.drama.features.searchdramafromtmdb;

import com.recody.recodybackend.drama.RecodyDramaApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyDramaApplication.class)
class DefaultSearchDramaFromTMDBHandlerTest {
    
    @Autowired
    SearchDramaFromTMDBHandler<TMDBSearchDramaResponse> handler;
    
    @Test
    @DisplayName( "search drama from tmdb" )
    void test01() {
        // given
        SearchDramaFromTMDB query = SearchDramaFromTMDB.builder().queryText( "오자크" )
                                            .language( "ko" )
                                                       .build();
    
        // when
        TMDBSearchDramaResponse response = handler.handle( query );
    
        // then
        assertThat(response).isNotNull();
        System.out.println( "response = " + response );
    }
    
}