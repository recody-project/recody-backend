package com.recody.recodybackend.movie.features.tmdb;

public class TMDB {
    public static final String POSTER_PATH_PREFIX = "https://image.tmdb.org/t/p/w500";
    
    public static String fullPosterPath(String posterPath) {
        if (posterPath == null) {
            throw new IllegalArgumentException("poster Path 가 null 입니다.");
        }
        return POSTER_PATH_PREFIX.concat(posterPath);
    }
}
