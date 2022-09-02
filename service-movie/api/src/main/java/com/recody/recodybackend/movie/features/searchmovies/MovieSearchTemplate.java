package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.features.searchmovies.request.MovieSearchRequest;

public interface MovieSearchTemplate {
    String execute(MovieSearchRequest request);
}