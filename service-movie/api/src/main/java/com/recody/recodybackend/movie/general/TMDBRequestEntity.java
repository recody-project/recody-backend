package com.recody.recodybackend.movie.general;

import com.recody.recodybackend.common.openapi.AbstractRequestEntity;

public abstract class TMDBRequestEntity extends AbstractRequestEntity {
    
    private static final String TMDB_URI = "https://api.themoviedb.org/3";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    private static final String TMDB_API_KEY_PARAM_NAME = "api_key";
    
    private final String path;
    
    protected TMDBRequestEntity(String path) {
        super(TMDB_URI, TMDB_API_KEY_PARAM_NAME, TMDB_LANGUAGE_PARAM_NAME);
        this.path = path;
        setPath(path);
        validatePath();
    }
    
    private void validatePath(){
        if (path == null) {
            throw new RuntimeException("Path 가 null 입니다.");
        }
    }
    
    public String getPath() { return path; }
}
