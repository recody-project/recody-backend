package com.recody.recodybackend.movie.features.getmovieInfo;

import com.recody.recodybackend.movie.features.searchmovies.request.TMDBRequestEntity;

public class MovieInfoRequestEntity extends TMDBRequestEntity {
    public MovieInfoRequestEntity(String path) {
        super(path);
    }
}
