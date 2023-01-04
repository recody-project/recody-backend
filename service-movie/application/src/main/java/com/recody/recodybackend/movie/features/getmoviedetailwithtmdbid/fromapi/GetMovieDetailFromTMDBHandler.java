package com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromapi;

import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb.GetMovieDetailWithTMDBId;

public interface GetMovieDetailFromTMDBHandler {
    
    TMDBFetchedMovieDetail handle(GetMovieDetailWithTMDBId command);
    
}
