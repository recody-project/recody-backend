package com.recody.recodybackend.movie.features.resolvegenres.fromdatabase;

import com.recody.recodybackend.movie.general.MovieGenre;

/*
* database 에서 장르정보를 찾는다.
* tmdbGenreId -> MovieGenre */
public interface GetMovieGenreFromDataBase {
    
    MovieGenre getMovieGenre(Integer tmdbGenreId);
}
