package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.contents.GenreIds;
import com.recody.recodybackend.common.data.QueryMetadata;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.Movies;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component( "defaultSearchMoviesHandler" )
@RequiredArgsConstructor
@Slf4j
class DefaultSearchMoviesHandler implements SearchMoviesHandler<Movies> {
    
    private final MovieRepository movieRepository;
    
    private final MovieMapper movieMapper;
    
    @Override
    public Movies handle(SearchMovies query) {
        log.debug( "handling query: {}", query );
        String movieName = query.getMovieName();
        PageRequest pageable = PageRequest.of( query.getPage() - 1, query.getSize() );
        Locale locale = Locale.forLanguageTag( query.getLanguage() );
        GenreIds genreIds = query.getGenreIds();
        
        Page<MovieEntity> movieEntitiesPage =
                movieRepository.findPagedByTitleLikeFilterByGenreIds( movieName, locale, pageable, genreIds );
        
        log.info( "DB 에서 {}개의 영화를 찾았습니다.", movieEntitiesPage.getContent().size() );
        
        List<Movie> movies = movieMapper.toMovie( movieEntitiesPage.getContent(), locale );
        
        QueryMetadata queryMetadata = new QueryMetadata( movieEntitiesPage );
        
        return new Movies( movies, queryMetadata );
    }
}
