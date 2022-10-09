package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailResult;

public interface MovieService {
    
    GetMovieDetailResult getMovieDetail(GetMovieDetail command);
    
}
