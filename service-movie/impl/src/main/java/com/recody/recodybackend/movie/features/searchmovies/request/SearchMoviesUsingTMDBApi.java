package com.recody.recodybackend.movie.features.searchmovies.request;

import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesUsingApi;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchMoviesUsingTMDBApi implements SearchMoviesUsingApi {
    
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    private static final String TMDB_MOVIE_SEARCH_PATH = "/search/movie";
    private final String movieName;
    private final TMDBAPIRequest delegate;
    
    private SearchMoviesUsingTMDBApi(String movieName, String language) {
        delegate = new TMDBAPIRequest(TMDB_MOVIE_SEARCH_PATH, language);
        this.movieName = movieName;
        initMovieName(movieName);
        log.trace("movieName set to: {}", movieName);
        validate();
    }
    
    public static SearchMoviesUsingTMDBApiBuilder builder(){
        return new SearchMoviesUsingTMDBApiBuilder();
    }
    
    @Override
    public APIRequest toAPIRequest() {
        return delegate;
    }
    
    private void initMovieName(String movieName) {
        delegate.addRequestParam(TMDB_QUERY_PARAM_NAME, movieName);
    }
    
    private void validate(){
        if (movieName == null) throw new IllegalArgumentException("Movie name was not set");
    }
    
    public static class SearchMoviesUsingTMDBApiBuilder {
        private String movieName;
        private String language;
    
        public SearchMoviesUsingTMDBApiBuilder movieName(String movieName){
            this.movieName = movieName;
            return this;
        }
    
        public SearchMoviesUsingTMDBApiBuilder language(String language){
            this.language = language;
            return this;
        }
    
        public SearchMoviesUsingTMDBApi build(){
            return new SearchMoviesUsingTMDBApi(movieName, language);
        }
    
        public SearchMoviesUsingTMDBApiBuilder korean(){
            this.language = "ko";
            return this;
        }
    }
}
