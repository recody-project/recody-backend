package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@ExtendWith( MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class ReactiveCatalogSearchMoviesHandlerTest {
    
//    @Autowired
//    CatalogSearchMoviesHandler<Mono<Movies>> catalogSearchMoviesHandler;
//
//
//    @Test
//    @DisplayName( "기능 테스트" )
//    void test01() {
//        // given
//        Mono<Movies> joy = catalogSearchMoviesHandler.handle( SearchMovies.builder()
//                                                                          .keyword( "kill" )
//                                                                          .genreIds( List.of("mg-1") )
//                                                                          .build() );
//
//        System.out.println(joy.block());
//
//        // when
//
//        // then
//    }
    
}