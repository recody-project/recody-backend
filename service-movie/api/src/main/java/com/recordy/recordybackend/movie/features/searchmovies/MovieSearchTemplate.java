package com.recordy.recordybackend.movie.features.searchmovies;

public interface MovieSearchTemplate {
    MovieSearchTemplate movieName(String movieName);
    MovieSearchTemplate language(String movieName);
    MovieSearchTemplate korean();
    String execute();
}