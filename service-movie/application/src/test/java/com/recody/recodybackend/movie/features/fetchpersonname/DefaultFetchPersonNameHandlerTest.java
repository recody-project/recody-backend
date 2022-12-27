package com.recody.recodybackend.movie.features.fetchpersonname;

import com.recody.recodybackend.movie.RecodyMovieApplication;
import com.recody.recodybackend.movie.TMDBPersonName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyMovieApplication.class)
@ExtendWith( MockitoExtension.class)
class DefaultFetchPersonNameHandlerTest {
    
    @Autowired
    FetchPersonNameHandler<TMDBPersonName> fetchPersonNameHandler;
    
    @ParameterizedTest
    @CsvSource(value = {"2563029", "6587", "103"})
    @DisplayName( "tmdb person id 로 한글 이름을 가져올 수 있다." )
    void test01(Integer tmdbPersonId) {
        // given
        TMDBPersonName name = fetchPersonNameHandler.handle( FetchPersonName.builder()
                                                                              .tmdbPersonId( tmdbPersonId )
                                                                              .build() );
        
        // when
    
        System.out.println(name);
        
        // then
    }
    
}