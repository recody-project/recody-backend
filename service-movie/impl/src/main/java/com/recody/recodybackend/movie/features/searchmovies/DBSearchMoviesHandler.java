package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.contents.GenreIds;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
class DBSearchMoviesHandler implements SearchMoviesHandler<MovieEntity> {
    private final MovieRepository movieRepository;
    
    @Override
    public List<MovieEntity> handle(SearchMovies command) {
        log.debug( "handling command: {}", command );
        String movieName = command.getMovieName();
        PageRequest pageable = PageRequest.of( command.getPage() - 1, command.getSize() );
        Locale locale = Locale.forLanguageTag( command.getLanguage() );
        List<MovieEntity> movieEntities = movieRepository.findByTitleLike( movieName, locale, pageable );
        log.info( "DB 에서 {}개의 영화를 찾았습니다.", movieEntities.size() );
        return movieEntities;
    }
    
    @Override
    public Page<MovieEntity> handlePage(SearchMovies command) {
        log.debug( "handling command: {}", command );
        String movieName = command.getMovieName();
        PageRequest pageable = PageRequest.of( command.getPage() - 1, command.getSize() );
        Locale locale = Locale.forLanguageTag( command.getLanguage() );
        GenreIds genreIds = command.getGenreIds();
        Page<MovieEntity> movieEntitiesPage = movieRepository.findPagedByTitleLikeFilterByGenreIds( movieName, locale, pageable, genreIds );
        log.info( "DB 에서 {}개의 영화를 찾았습니다.", movieEntitiesPage.getContent().size() );
        return movieEntitiesPage;
    }
}
