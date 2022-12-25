package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.contents.GenreIds;
import com.recody.recodybackend.common.data.QueryMetadata;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.features.applicationevent.MoviesSearched;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.web.SearchMoviesResult;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component("tmdbSearchMoviesHandler")
@RequiredArgsConstructor
@Slf4j
class TMDBSearchMoviesHandlerV2 implements SearchMoviesHandlerV2<SearchMoviesResult>{
    
    public static final int MINIMUM_SEARCH_RESULT_SIZE = 10;
    private final SearchMoviesHandler<TMDBMovieSearchNode> tMDBSearchMoviesHandler;
    private final MovieMapper movieMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MovieRepository movieRepository;
    
    @Override
    public SearchMoviesResult handle(SearchMovies query) {
        Locale locale = Locale.forLanguageTag( query.getLanguage() );
        
        String movieName = query.getMovieName();
        PageRequest pageable = PageRequest.of( query.getPage() - 1, query.getSize() );
        GenreIds genreIds = query.getGenreIds();
        Page<MovieEntity> moviesFromDB = movieRepository.findPagedByTitleLikeFilterByGenreIds( movieName, locale, pageable, genreIds );

        log.info( "DB 에서 {}개의 영화를 찾았습니다.", moviesFromDB.getSize() );
        
        if ( moviesFromDB.getSize() > MINIMUM_SEARCH_RESULT_SIZE ) {
            List<TMDBSearchedMovie> tmdbSearchedMovies = movieMapper.toTMDBMovie( moviesFromDB.getContent(),
                                                                                  locale );
            return SearchMoviesResult.builder()
                                     .metadata( new QueryMetadata( moviesFromDB, true ) )
                                     .movies( tmdbSearchedMovies )
                                     .build();
        }
    
        // API 에서 가져옴.
        Page<TMDBMovieSearchNode> tmdbMoviesPage = tMDBSearchMoviesHandler.handlePage( query );
    
        applicationEventPublisher.publishEvent(
                MoviesSearched.builder().tmdbMovies( tmdbMoviesPage.getContent() ).locale( locale ).build() );
    
        List<TMDBSearchedMovie> movies = movieMapper.toTMDBMovie( tmdbMoviesPage.getContent() );
    
        return SearchMoviesResult.builder()
                                 .metadata( new QueryMetadata( tmdbMoviesPage, false ) )
                                 .movies( movies )
                                 .build();
    }
}
