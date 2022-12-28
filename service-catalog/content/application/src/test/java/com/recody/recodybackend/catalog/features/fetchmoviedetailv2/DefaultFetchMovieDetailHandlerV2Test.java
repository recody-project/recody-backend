package com.recody.recodybackend.catalog.features.fetchmoviedetailv2;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import com.recody.recodybackend.movie.MovieDetailViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@ExtendWith( MockitoExtension.class )
@SpringBootTest
@ActiveProfiles( "test" )
@ContextConfiguration( classes = RecodyCatalogApplication.class )
class DefaultFetchMovieDetailHandlerV2Test {
    @Autowired
    private FetchMovieDetailHandlerV2<MovieDetailViewModel> fetchMovieDetailHandlerV2;
    
    
    // 서버를 띄우고 난 후에 테스트 해야합니다.
    // 요청을 받을 api 서버를 mocking 하는 방법을 사용하려면 MockServer, WireMock 등을 사용할 수 있으나
    // 쉽지 않아서 다음에 해보는 것으로 했습니다.
    @Test
    void handle() {
//        MovieDetailViewModel viewModel =
//                fetchMovieDetailHandlerV2.handle( FetchMovieDetailV2.builder()
//                                                                    .movieId( "mov-583" )
//                                                                    .locale( Locale.KOREAN )
//                                                                    .build() );
//        System.out.println( viewModel );

//        assertThat( viewModel ).isNotNull();
        
    }
}