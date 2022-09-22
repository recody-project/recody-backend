package com.recody.recodybackend.movie.features.test;

import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.common.openapi.APIFeature;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

public class NewTMDBMovieSearchFeature implements APIFeature {
    
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    private static final String TMDB_MOVIE_SEARCH_PATH = "/search/movie";
    private final APIRequest delegate;
    
    public NewTMDBMovieSearchFeature(String movieName, String language) {
        delegate = new TMDBAPIRequest();
        delegate.setPath(TMDB_MOVIE_SEARCH_PATH);
        delegate.addRequestParam(TMDB_QUERY_PARAM_NAME, movieName);
        delegate.addRequestParam(TMDB_LANGUAGE_PARAM_NAME, language, false);
    }
    
    @Override
    public APIRequest toAPIRequest() {
        return delegate;
    }
    
    @Override
    public String toString() {
        return "[{\"NewTMDBMovieSearchAPIRequest\":{" + "\"path\":" + ((path != null) ? ("\"" + path + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
