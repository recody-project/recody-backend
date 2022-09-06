package com.recody.recodybackend.movie.features.searchmovies;

public interface MovieSearchService {
    SearchMovieResponse handle(SearchMovie command);
}
