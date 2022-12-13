package com.recody.recodybackend.movie.features.getmoviedetail.fromapi;

import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetail;

public interface GetMovieDetailFromTMDBHandler {
    
    TMDBFetchedMovieDetail handle(GetMovieDetail command);
    
}
