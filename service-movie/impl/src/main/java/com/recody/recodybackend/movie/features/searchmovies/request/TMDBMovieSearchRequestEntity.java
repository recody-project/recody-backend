package com.recody.recodybackend.movie.features.searchmovies.request;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TMDBMovieSearchRequestEntity extends TMDBRequestEntity implements MovieSearchRequestEntity {
    
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    private static final String MOVIE_SEARCH_PATH = "/search/movie";
    private final String movieName;
    
    private TMDBMovieSearchRequestEntity(String movieName, String language) {
        super(MOVIE_SEARCH_PATH);
        initMovieName(movieName);
        this.movieName = movieName;
        this.setLanguage(language);
        validate();
    }
    
    private void initMovieName(String movieName) {
        this.addRequestParam(TMDB_QUERY_PARAM_NAME, movieName);
    }
    
    public void setMovieName(String movieName){
        this.addRequestParam(TMDB_QUERY_PARAM_NAME, movieName);
        log.trace("movieName set to: {}", movieName);
    }
    
    @Override
    public void setLanguage(String language){
        super.setLanguage(language);
        log.trace("language set to: {}", language);
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
    
        public TMDBMovieSearchRequestEntity build(){
            return new TMDBMovieSearchRequestEntity(movieName, language);
        }
    
        public TMDBMovieSearchRequestDelegateBuilder korean(){
            this.language = "ko";
            return this;
        }
    }
}
