package com.recody.recodybackend.movie.features.resolvegenres;

import com.recody.recodybackend.movie.general.MovieGenre;

import java.util.List;
import java.util.Map;

public interface MovieGenreResolver {
    Map<Integer, List<MovieGenre>> handle(ResolveMovieGenres command);
}
