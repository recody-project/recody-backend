package com.recody.recodybackend.movie.features.test;

import com.recody.recodybackend.movie.general.TMDBAPIRequest;

public class NewTMDBMovieSearchAPIRequest extends TMDBAPIRequest {
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    private static final String MOVIE_SEARCH_PATH = "/search/movie";
    
    public NewTMDBMovieSearchAPIRequest(String movieName, String language) {
        super(MOVIE_SEARCH_PATH, language);
        addRequestParam(TMDB_QUERY_PARAM_NAME, movieName);
    }
    
    @Override
    public String toString() {
        return "[{\"NewTMDBMovieSearchAPIRequest\":{" + "\"path\":" + ((path != null) ? ("\"" + path + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
