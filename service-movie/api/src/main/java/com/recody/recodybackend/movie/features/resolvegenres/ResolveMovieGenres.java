package com.recody.recodybackend.movie.features.resolvegenres;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class ResolveMovieGenres {
    private Map<Integer, List<Integer>> genreIds;
}
