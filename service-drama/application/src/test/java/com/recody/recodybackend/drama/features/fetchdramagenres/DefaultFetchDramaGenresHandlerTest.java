package com.recody.recodybackend.drama.features.fetchdramagenres;

import com.recody.recodybackend.drama.RecodyDramaApplication;
import com.recody.recodybackend.drama.tmdb.genre.TMDBDramaGenres;
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
class DefaultFetchDramaGenresHandlerTest {
    
    @Autowired
    FetchDramaGenresHandler<TMDBDramaGenres> fetchDramaGenresHandler;
    
    @Test
    @DisplayName( "장르 가져오기 테스트" )
    void test01() {
        // given
        TMDBDramaGenres response = fetchDramaGenresHandler.handle(FetchDramaGenres.builder()
                                                                                  .locale( Locale.KOREAN )
                                                                                  .build() );
        
    
        // when
        System.out.println(response );
        assertThat( response ).isNotNull();
        assertThat( response.getValues() ).isNotEmpty();
        
        // then
    }
    
}