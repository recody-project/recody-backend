package com.recody.recodybackend.movie.features.getmovieDetail;

import com.recody.recodybackend.movie.general.TMDBRequestEntity;

public class TMDBGetMovieDetail implements GetMovieDetail {
    
    private static final String PATH = "/movie/";
    private final TMDBRequestEntity delegate;
    
    public TMDBGetMovieDetail(String movieId, String language) {
        delegate = new TMDBRequestEntity(PATH + movieId, language);
    }
    
    @Override
    public TMDBRequestEntity toEntity() {
        return delegate;
    }

    @Override
    public String getLanguage() {
        return delegate.getLanguage();
    }
}
