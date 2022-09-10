package com.recody.recodybackend.movie.features.test;

import com.recody.recodybackend.movie.general.TMDBRequestEntity;

public class NewTMDBMovieSearchRequestEntity extends TMDBRequestEntity {
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    private static final String MOVIE_SEARCH_PATH = "/search/movie";
    
    public NewTMDBMovieSearchRequestEntity(String movieName, String language) {
        super(MOVIE_SEARCH_PATH, language);
        addRequestParam(TMDB_QUERY_PARAM_NAME, movieName);
    }
    
    @Override
    public String toString() {
        return "[{\"NewTMDBMovieSearchRequestEntity\":{" + "\"API_KEY_PARAM_NAME\":" + ((API_KEY_PARAM_NAME != null) ? ("\"" + API_KEY_PARAM_NAME + "\"") : null) + ", \"LANGUAGE_PARAM_NAME\":" + ((LANGUAGE_PARAM_NAME != null) ? ("\"" + LANGUAGE_PARAM_NAME + "\"") : null) + ", \"language\":" + ((language != null) ? ("\"" + language + "\"") : null) + ", \"apiKey\":" + ((apiKey != null) ? ("\"" + apiKey + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
