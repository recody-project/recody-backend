package com.recordy.recordybackend.movie.features.searchmovies;

import com.recordy.recordybackend.movie.features.searchmovies.request.MovieSearchRequest;

public interface MovieSearchTemplate {
    String execute(MovieSearchRequest request);
}