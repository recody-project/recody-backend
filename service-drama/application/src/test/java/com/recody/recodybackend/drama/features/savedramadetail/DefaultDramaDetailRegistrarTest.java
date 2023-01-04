package com.recody.recodybackend.drama.features.savedramadetail;

import com.recody.recodybackend.drama.RecodyDramaApplication;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.features.fetchdramadetail.FetchDramaDetail;
import com.recody.recodybackend.drama.features.fetchdramadetail.FetchDramaDetailHandler;
import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyDramaApplication.class)
class DefaultDramaDetailRegistrarTest {
    public static final int TMDB_ID = 69740;
    
    @Autowired
    DramaDetailRegistrar<TMDBDramaDetail, DramaEntity> dramaDetailRegistrar;
    
    @Autowired
    FetchDramaDetailHandler<TMDBDramaDetail> fetchDramaDetailHandler;
    
    @Autowired
    DramaRepository dramaRepository;
    
    @Test
    @DisplayName( "기능 테스트" )
    void test01() {
        // given
        TMDBDramaDetail detail = fetchDramaDetailHandler.handle( FetchDramaDetail.builder()
                                                                                 .tmdbId( TMDB_ID )
                                                                                 .locale( Locale.KOREAN )
                                                                                 .build() );
    
        // when
        DramaEntity dramaEntity = dramaDetailRegistrar.handle( detail, Locale.KOREAN );
        
        // then
        System.out.println( "dramaEntity = " + dramaEntity );
        assertThat(dramaEntity).isNotNull();
    }
}