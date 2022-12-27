package com.recody.recodybackend.movie.features.getmoviegenres;

import com.recody.recodybackend.movie.MovieGenre;
import com.recody.recodybackend.movie.MovieGenreViewModel;
import com.recody.recodybackend.movie.MovieGenres;
import com.recody.recodybackend.movie.features.resolvegenre.MovieGenreResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMovieGenresHandler implements GetMovieGenresHandler {
    
    private final MovieGenreResolver movieGenreResolver;
    
    @Override
    public MovieGenres handle() {
        log.debug( "영화 장르를 찾습니다." );
        List<MovieGenre> movieGenres = movieGenreResolver.allMovieGenres();
        log.info( "영화 장르들을 찾았습니다.: {}개", movieGenres.size() );
        return new MovieGenres( movieGenres.stream()
                                           .map( MovieGenreViewModel::new )
                                           .collect( Collectors.toList() ) );
    }
}
