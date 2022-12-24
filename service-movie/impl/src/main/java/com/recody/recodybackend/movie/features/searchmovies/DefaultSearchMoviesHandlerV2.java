package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.Movies;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component("defaultSearchMoviesHandler")
@RequiredArgsConstructor
@Slf4j
class DefaultSearchMoviesHandlerV2 implements SearchMoviesHandlerV2<Movies>{
    
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    
    @Override
    public Movies handle(SearchMovies query) {
        log.debug( "handling query: {}", query );
        String movieName = query.getMovieName();
        PageRequest pageable = PageRequest.of( query.getPage() - 1, query.getSize() );
        Locale locale = Locale.forLanguageTag( query.getLanguage() );
        List<MovieEntity> movieEntities = movieRepository.findByTitleLike( movieName, locale, pageable );
        log.info( "DB 에서 {}개의 영화를 찾았습니다.", movieEntities.size() );
        List<Movie> movies = movieMapper.toMovie( movieEntities, locale );
        return new Movies( movies );
    }
}
