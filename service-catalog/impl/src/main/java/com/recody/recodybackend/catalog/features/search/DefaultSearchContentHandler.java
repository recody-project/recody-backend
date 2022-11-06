package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.catalog.PersonalizedContent;
import com.recody.recodybackend.catalog.PersonalizedMovie;
import com.recody.recodybackend.catalog.features.personalize.ContentPersonalizer;
import com.recody.recodybackend.catalog.features.search.movies.ReactiveSearchMoviesHandler;
import com.recody.recodybackend.catalog.features.search.movies.SearchMovies;
import com.recody.recodybackend.catalog.features.search.movies.CatalogSearchMoviesHandler;
import com.recody.recodybackend.catalog.web.SearchContentResponse;
import com.recody.recodybackend.catalog.web.SearchContentWithFiltersResponse;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSearchContentHandler implements SearchContentHandler {
    
    private final CatalogSearchMoviesHandler catalogSearchMoviesHandler;
    private final ReactiveSearchMoviesHandler reactiveSearchMoviesHandler;
    private final ContentPersonalizer<Movie, PersonalizedMovie> movieContentPersonalizer;
    
    @Override
    public SearchContentResponse handle(SearchContent command) {
        
        String keyword = command.getKeyword();
        BasicCategory category = command.getCategory();
        Long userId = command.getUserId();
        List<PersonalizedContent> personalizedContentDetails = new ArrayList<>();
        
        if ( category.equals( BasicCategory.Movie ) ) {
            List<Movie> movies = catalogSearchMoviesHandler.handle(
                    SearchMovies.builder().keyword( keyword ).language( command.getLanguage() ).build() );
            for (Movie movie : movies) {
                personalizedContentDetails.add( movieContentPersonalizer.personalize( movie, userId ) );
            }
        }
        else {
            // 현재는 영화만 지원한다.
            throw new UnsupportedCategoryException();
        }
        
        return SearchContentResponse.builder()
                                    .contents( personalizedContentDetails )
                                    .total( personalizedContentDetails.size() )
                                    .build();
    }
    
    @Override
    public SearchContentWithFiltersResponse handle(SearchContentWithFilters command) {
        log.debug( "handling command: {}", command );
        String keyword = command.getKeyword();
        String language = command.getLanguage();
        List<BasicCategory> categories = command.getCategories();
        
        Mono<List<TMDBSearchedMovie>> moviesMono = Mono.empty();
        for (BasicCategory category : categories) {
            if (category.equals( BasicCategory.Movie )){
                moviesMono = reactiveSearchMoviesHandler.handleTmdb( SearchMovies.builder()
                                                                             .keyword( keyword )
                                                                             .language( language )
                                                                             .build() );
            }
        }
        log.info( "검색 결과를 반환합니다." );
        return SearchContentWithFiltersResponse.builder()
                                               .movies( moviesMono.block( Duration.ofSeconds(10L)) )
                                               .build();
    }
}
