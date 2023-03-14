package com.recody.recodybackend.drama.tmdb;

public class TMDB {
    public static final String POSTER_PATH_PREFIX = "https://image.tmdb.org/t/p/w500";
    
    public static final String TMDB_BASE_URI = "https://api.themoviedb.org/3";
    
    public static final String TMDB_WEB_CLIENT = "TMDBWebClient";
    public static final String DIRECTOR = "Director";
    public static final String ACTING = "Acting";
    public static final String DIRECTING = "Directing";
    public static final int ACTOR_MAX_SIZE = 5;
    public static final int DIRECTOR_MAX_SIZE = 5;
    
    public static String fullPosterPath(String posterPath) {
        if (posterPath == null) {
            return null;
        }
        return POSTER_PATH_PREFIX.concat(posterPath);
    }
}
