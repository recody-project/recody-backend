package com.recody.recodybackend.drama.features.fetchdramacredit;

import com.recody.recodybackend.drama.RecodyDramaApplication;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCreditResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/*
* 한국어 이름은 오지 않으므로 따로 요청해야한다.
* */
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyDramaApplication.class)
class DefaultFetchDramaCreditHandlerTest {
    
    
    public static final int TMDB_ID = 69740;
    @Autowired
    private FetchDramaCreditHandler<TMDBDramaCreditResponse> fetchDramaCreditHandler;
    
    @Test
    @DisplayName( "Credit 기본 테스트" )
    void test01() {
        // given
        TMDBDramaCreditResponse response = fetchDramaCreditHandler.handle(
                FetchDramaCredit.builder().tmdbId( TMDB_ID ).locale( Locale.KOREAN ).build() );
        
        // when
        System.out.println( "response = " + response );
        assertThat(response).isNotNull();
        
        // then
    }
}