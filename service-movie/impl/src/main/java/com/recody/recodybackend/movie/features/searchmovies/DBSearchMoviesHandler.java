package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DBSearchMoviesHandler implements SearchMoviesHandler<MovieEntity> {
    private final MovieRepository movieRepository;
    
    @Override
    public List<MovieEntity> handle(SearchMovies command) {
        log.debug( "handling command: {}", command );
        String movieName = command.getMovieName();
        Locale locale = Locale.forLanguageTag( command.getLanguage() );
        List<MovieEntity> movieEntities = movieRepository.findByTitleLike( movieName, locale );
        log.info( "DB 에서 {}개의 영화를 찾았습니다.", movieEntities.size() );
        return movieEntities;
    }
}
