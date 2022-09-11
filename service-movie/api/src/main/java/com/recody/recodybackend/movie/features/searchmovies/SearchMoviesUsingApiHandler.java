package com.recody.recodybackend.movie.features.searchmovies;

public interface SearchMoviesUsingApiHandler {
    String handleToString(SearchMoviesUsingApi request);
    
    SearchMoviesUsingApiResponse handleToJson(SearchMoviesUsingApi request);
}