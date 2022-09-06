package com.recody.recodybackend.movie.features.searchmovies.request;

public interface MovieSearchTemplate {
    String execute(MovieSearchRequestEntity request);
    
    MovieSearchResult executeToJson(MovieSearchRequestEntity request);
}