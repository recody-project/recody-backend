package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.catalog.features.search.drama.CatalogSearchDramasHandler;
import com.recody.recodybackend.catalog.features.search.drama.SearchDramas;
import com.recody.recodybackend.catalog.features.search.movies.CatalogSearchMoviesHandler;
import com.recody.recodybackend.catalog.features.search.movies.SearchMovies;
import com.recody.recodybackend.catalog.web.SearchContentWithFiltersResponseV3;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.drama.Dramas;
import com.recody.recodybackend.movie.Movies;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSearchContentWithFiltersHandler implements SearchContentWithFiltersHandler<SearchContentWithFiltersResponseV3> {
    
    private final CatalogSearchMoviesHandler<Mono<Movies>> reactiveSearchMoviesHandler;
    private final CatalogSearchDramasHandler<Mono<Dramas>> reactiveSearchDramasHandler;
    
    @Override
    public SearchContentWithFiltersResponseV3 handle(SearchContentWithFilters command) {
        log.debug( "handling command: {}", command );
        String keyword = command.getKeyword();
        String language = command.getLanguage();
        List<BasicCategory> categories = command.getCategories();
        
        Mono<Movies> moviesMono = Mono.empty();
        Mono<Dramas> dramasMono = Mono.empty();
        for (BasicCategory category : categories) {
            if ( category.equals( BasicCategory.Movie ) ) {
                moviesMono = reactiveSearchMoviesHandler.handle(
                        SearchMovies.builder()
                                    .keyword( keyword )
                                    .language( language )
                                    .genreIds( command.getGenreIds() )
                                    .page( command.getPage() )
                                    .build() );
            }
            else if ( category.equals( BasicCategory.Drama ) ) {
                dramasMono = reactiveSearchDramasHandler.handle(
                        SearchDramas.builder()
                                    .keyword( keyword )
                                    .locale( Locale.forLanguageTag( language ) )
                                    .genreIds( command.getGenreIds() )
                                    .page( command.getPage() )
                                    .build() );
            }
        }
        log.info( "검색 결과를 반환합니다." );
        return SearchContentWithFiltersResponseV3.builder()
                                                 .movies( moviesMono.block( Duration.ofSeconds( 10L ) ) )
                                                 .dramas( dramasMono.block( Duration.ofSeconds( 10L ) ) )
                                                 .build();
    }
}
