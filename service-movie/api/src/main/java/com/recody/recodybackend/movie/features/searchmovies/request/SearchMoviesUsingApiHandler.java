package com.recody.recodybackend.movie.features.searchmovies.request;

public interface SearchMoviesUsingApiHandler {
    String handleToString(SearchMoviesUsingApi request);
    
    SearchMoviesUsingApiResponse handleToJson(SearchMoviesUsingApi request);
}