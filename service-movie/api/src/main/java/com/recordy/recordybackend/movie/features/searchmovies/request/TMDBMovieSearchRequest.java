package com.recordy.recordybackend.movie.features.searchmovies.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;

import java.net.URI;

@Slf4j
public class TMDBMovieSearchRequest extends MovieSearchRequest {
    
    private static final String TMDB_API_KEY_PARAM_NAME = "api_key";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    
    private static final String TMDB_API_URI = "https://api.themoviedb.org/3/search/movie";
    
    private TMDBMovieSearchRequest(String movieName, String language) {
        super(TMDB_API_URI, TMDB_API_KEY_PARAM_NAME, TMDB_QUERY_PARAM_NAME, TMDB_LANGUAGE_PARAM_NAME);
        setMovieName(movieName);
        setLanguage(language);
    }
    
    public static MovieSearchRequestBuilder builder(){
        return new MovieSearchRequestBuilder();
    }
    
    @Override
    protected RequestEntity<Void> makeEntity(URI uri, HttpHeaders headers) {
        return RequestEntity.get(uri).headers(headers).build();
    }
    
    
    /*=========================== Builder ============================*/
    
    public static class MovieSearchRequestBuilder{
        private String movieName;
        private String language;
        
        public MovieSearchRequestBuilder movieName(String movieName){
            this.movieName = movieName;
            return this;
        }
    
        public MovieSearchRequestBuilder language(String language){
            this.language = language;
            return this;
        }
    
        public TMDBMovieSearchRequest build(){
            return new TMDBMovieSearchRequest(movieName, language);
        }
        
        public MovieSearchRequestBuilder korean(){
            this.language = "ko";
            return this;
        }
    }
}
