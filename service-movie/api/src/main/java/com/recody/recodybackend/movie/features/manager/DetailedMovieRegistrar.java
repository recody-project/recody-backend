package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.common.contents.register.AsyncContentRegistrar;
import com.recody.recodybackend.movie.features.getmoviedetail.MovieInfo;

public interface DetailedMovieRegistrar<T> extends AsyncContentRegistrar<MovieInfo,T> {

}
