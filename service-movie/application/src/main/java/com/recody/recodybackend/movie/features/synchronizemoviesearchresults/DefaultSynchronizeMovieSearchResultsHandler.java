package com.recody.recodybackend.movie.features.synchronizemoviesearchresults;

import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchResponse;
import com.recody.recodybackend.movie.features.searchmoviesfromtmdb.SearchMoviesFromTMDB;
import com.recody.recodybackend.movie.features.searchmoviesfromtmdb.SearchMoviesFromTMDBHandler;
import com.recody.recodybackend.movie.search.MovieSearchKeyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSynchronizeMovieSearchResultsHandler implements SynchronizeMovieSearchResultsHandler<Void> {
    
    private static final List<Locale> locales = List.of( Locale.KOREAN, Locale.ENGLISH );
    private final SearchMoviesFromTMDBHandler<TMDBMovieSearchResponse> searchMoviesFromTMDBHandler;
    private final MovieManager movieManager;
    
    @Override
    @Transactional
    public Void handle(MovieSearchKeyword keyword) {
        log.debug( "synchronizing search keyword: {}", keyword );
        for (Locale locale : locales) {
            TMDBMovieSearchResponse response = searchMoviesFromTMDBHandler.handle(
                    SearchMoviesFromTMDB.builder()
                                        .movieName( keyword.getValue() )
                                        .language( locale.getLanguage() )
                                        .build() );
            
            movieManager.movie().register( response.getResults(), locale );
        }
        return null;
    }
}
