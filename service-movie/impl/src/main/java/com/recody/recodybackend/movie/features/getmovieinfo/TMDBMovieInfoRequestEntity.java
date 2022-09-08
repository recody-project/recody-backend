package com.recody.recodybackend.movie.features.getmovieinfo;

import com.recody.recodybackend.movie.general.TMDBRequestEntity;
import org.springframework.http.RequestEntity;

public class TMDBMovieInfoRequestEntity implements MovieInfoRequestEntity {
    
    private static final String PATH = "/movie/";
    private final TMDBRequestEntity delegate;
    
    public TMDBMovieInfoRequestEntity(String movieId, String language) {
        delegate = new TMDBRequestEntity(PATH + movieId, language);
    }
    
    @Override
    public RequestEntity<Void> toEntity() {
        return delegate.toEntity();
    }
    
    @Override
    public void setApiKey(String apiKey) {
        delegate.setApiKey(apiKey);
    }
    
    @Override
    public String getLanguage() {
        return delegate.getLanguage();
    }
}
