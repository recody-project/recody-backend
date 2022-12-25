package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.data.QueryMetadata;
import com.recody.recodybackend.movie.data.movie.MovieMapper;
import com.recody.recodybackend.movie.features.applicationevent.MoviesSearched;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchResponse;
import com.recody.recodybackend.movie.features.searchmoviesfromtmdb.SearchMoviesFromTMDB;
import com.recody.recodybackend.movie.features.searchmoviesfromtmdb.SearchMoviesFromTMDBHandler;
import com.recody.recodybackend.movie.web.SearchMoviesResult;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component( "tmdbSearchMoviesHandler" )
@RequiredArgsConstructor
@Slf4j
class TMDBSearchMoviesHandler implements SearchMoviesHandler<SearchMoviesResult> {
    
    private final SearchMoviesFromTMDBHandler<TMDBMovieSearchResponse> searchMoviesFromTMDBHandler;
    private final MovieMapper movieMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    
    @Override
    public SearchMoviesResult handle(SearchMovies query) {
        Locale locale = Locale.forLanguageTag( query.getLanguage() );
        
        TMDBMovieSearchResponse response =
                searchMoviesFromTMDBHandler.handle(
                        SearchMoviesFromTMDB.builder()
                                            .movieName( query.getMovieName() )
                                            .language( query.getLanguage() )
                                            .page( query.getPage() )
                                            .build() );
        Integer page = response.getPage();
        Integer totalPages = response.getTotalPages();
        List<TMDBMovieSearchNode> movies = response.getResults();
        
        applicationEventPublisher.publishEvent(
                MoviesSearched.builder().tmdbMovies( movies ).locale( locale ).build() );
        
        List<TMDBSearchedMovie> mappedMovies = movieMapper.toTMDBMovie( movies );
        
        return SearchMoviesResult.builder()
                                 .metadata( new QueryMetadata( mappedMovies.size(), page, totalPages ) )
                                 .movies( mappedMovies )
                                 .build();
    }
}
