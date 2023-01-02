package com.recody.recodybackend.drama.features.fetchdramadetail;

import com.recody.recodybackend.drama.RecodyDramaApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyDramaApplication.class)
class DefaultFetchDramaDetailHandlerTest {
    
    public static final int TMDB_ID = 69740;
    @Autowired
    private FetchDramaDetailHandler<TMDBDramaDetail> fetchDramaDetailHandler;
    
    @Test
    @DisplayName( "기본 테스트" )
    void test01() {
        // given
        TMDBDramaDetail response = fetchDramaDetailHandler.handle(
                FetchDramaDetail.builder().tmdbId( TMDB_ID ).locale( Locale.KOREAN ).build() );
        
        // when
        System.out.println( "response = " + response );
        assertThat(response).isNotNull();
        
        // then
    }
    
    
}