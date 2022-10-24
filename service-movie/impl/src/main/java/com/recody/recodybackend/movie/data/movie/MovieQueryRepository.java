package com.recody.recodybackend.movie.data.movie;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface MovieQueryRepository {
    
    List<MovieEntity> findByTitleLike(String title, Locale locale);
    Optional<MovieEntity> findByTmdbIdFetchJoin(Integer tmdbId, Locale locale);

}
