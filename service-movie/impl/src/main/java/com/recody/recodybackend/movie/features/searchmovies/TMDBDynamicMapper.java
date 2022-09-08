package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.features.resolvecontentroot.ResolveContentRootResult;
import com.recody.recodybackend.movie.general.MovieGenre;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TMDBDynamicMapper implements MovieSearchMapper.DynamicMapper {
    //TODO: 구현
    
    private final SearchMovieResponse response;
    
    public TMDBDynamicMapper(SearchMovieResponse response) { this.response = response; }
    
    @Override
    public MovieSearchMapper.DynamicMapper requestedLanguage(Locale locale) {
        this.response.setRequestedLanguage(locale);
        return this;
    }
    
    @Override
    public MovieSearchMapper.DynamicMapper genreIds(Map<Integer, List<MovieGenre>> movieGenres) {
        List<SingleMovieSpec> movies = this.response.getMovies();
        for (SingleMovieSpec movie : movies) {
            // 각각의 영화 정보에 장르를 세팅한다.
            Integer movieId = movie.getMovieId();
            try {
                List<MovieGenre> genres = movieGenres.get(movieId);
                movie.setGenres(genres);
            } catch (Exception exception){
                exception.printStackTrace();
                throw new RuntimeException("매핑할 장르를 찾지 못했습니다. Genre Resolver 가 장르 ID를 가지고 있지 않습니다.");
            }
        }
        return this;
    }
    
    // TODO: 구현
    @Override
    public MovieSearchMapper.DynamicMapper contentRoot(ResolveContentRootResult rootIdResult) {
        return this;
    }
    
    @Override
    public SearchMovieResponse get() {
        return response;
    }
    
    public enum MappingType{
        GenreIds, RootId,
    }
}
