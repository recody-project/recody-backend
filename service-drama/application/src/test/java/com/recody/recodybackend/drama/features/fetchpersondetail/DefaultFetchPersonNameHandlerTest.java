package com.recody.recodybackend.drama.features.fetchpersondetail;

import com.recody.recodybackend.drama.RecodyDramaApplication;
import com.recody.recodybackend.drama.tmdb.TMDBPersonName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyDramaApplication.class)
class DefaultFetchPersonNameHandlerTest {
    
    @Autowired
    private FetchPersonNameHandler<TMDBPersonName> fetchPersonNameHandler;
    
    public static final Integer personId = 23532;
    
    @Test
    @DisplayName( "기능 테스트" )
    void test01() {
        // given
        TMDBPersonName name = fetchPersonNameHandler.handle( FetchPersonName.builder()
                                                                              .tmdbPersonId( personId )
                                                                              .build() );
    
    
        // when
        System.out.println( name );
        assertThat( name ).isNotNull();
        // then
    }
    
}