package com.recody.recodybackend.movie.features.searchmovies.request;

import com.recody.recodybackend.movie.general.TMDBRequestEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchMoviesUsingTMDBApi implements SearchMoviesUsingApi {
    
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    private static final String TMDB_MOVIE_SEARCH_PATH = "/search/movie";
    private final String movieName;
    private final TMDBRequestEntity delegate;
    
    private SearchMoviesUsingTMDBApi(String movieName, String language) {
        delegate = new TMDBRequestEntity(TMDB_MOVIE_SEARCH_PATH, language);
        initMovieName(movieName);
        this.movieName = movieName;
        delegate.addRequestParam(TMDB_QUERY_PARAM_NAME, movieName);
        log.trace("movieName set to: {}", movieName);
        validate();
    }
    
    @Override
    public TMDBRequestEntity toEntity() {
        return delegate;
    }
    
    @Override
    public String getLanguage() {
        return delegate.getLanguage();
    }
    
    @Override
    public void setApiKey(String apiKey) {
        delegate.setApiKey(apiKey);
    }
    
    private void initMovieName(String movieName) {
        delegate.addRequestParam(TMDB_QUERY_PARAM_NAME, movieName);
    }
    
    private void validate(){
        if (movieName == null) throw new IllegalArgumentException("Movie name was not set");
    }
    
    public static TMDBMovieSearchRequestDelegateBuilder builder(){
        return new TMDBMovieSearchRequestDelegateBuilder();
    }
    
    public static class TMDBMovieSearchRequestDelegateBuilder{
        private String movieName;
        private String language;
    
        public TMDBMovieSearchRequestDelegateBuilder movieName(String movieName){
            this.movieName = movieName;
            return this;
        }
    
        public TMDBMovieSearchRequestDelegateBuilder language(String language){
            this.language = language;
            return this;
        }
    
        public SearchMoviesUsingTMDBApi build(){
            return new SearchMoviesUsingTMDBApi(movieName, language);
        }
    
        public TMDBMovieSearchRequestDelegateBuilder korean(){
            this.language = "ko";
            return this;
        }
    }
}
