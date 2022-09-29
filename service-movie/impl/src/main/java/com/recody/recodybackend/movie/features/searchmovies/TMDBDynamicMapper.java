package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.features.resolvecontentroot.ResolveContentRootResult;
import com.recody.recodybackend.movie.general.MovieGenre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TMDBDynamicMapper implements SearchMoviesMapper.DynamicMapper {
    
    private final SearchMoviesResult response;
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    public TMDBDynamicMapper(SearchMoviesResult response) { this.response = response; }
    
    @Override
    public SearchMoviesMapper.DynamicMapper requestedLanguage(Locale locale) {
        response.setRequestedLanguage(locale);
        return this;
    }
    
    @Override
    public SearchMoviesMapper.DynamicMapper genreIds(Map<Integer, List<MovieGenre>> movieGenres) {
        List<Movie> movies = this.response.getMovies();
        for (Movie movie : movies) {
            // 각각의 영화 정보에 장르를 세팅한다.
            Integer movieId = movie.getTmdbId();
            try {
                List<MovieGenre> genres = movieGenres.get(movieId);
                movie.setGenres(genres);
            } catch (Exception exception){
                log.warn("Genre resolver 가 장르정보를 넘겨주지 못했습니다.  exception: {}", exception.getMessage());
                throw new RuntimeException("매핑할 장르를 찾지 못했습니다. Genre Resolver 가 장르 ID를 가지고 있지 않습니다.");
            }
        }
        return this;
    }
    
    // TODO: 구현
    @Override
    public SearchMoviesMapper.DynamicMapper contentRoot(ResolveContentRootResult rootIdResult) {
        return this;
    }
    
    @Override
    public SearchMoviesResult get() {
        return response;
    }
    
    public enum MappingType{
        GenreIds, RootId,
    }
}
