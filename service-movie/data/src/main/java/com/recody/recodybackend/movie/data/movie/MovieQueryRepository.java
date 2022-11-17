package com.recody.recodybackend.movie.data.movie;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface MovieQueryRepository {
    
    List<MovieEntity> findByTitleLike(String title, Locale locale, Pageable pageable);
    Optional<MovieEntity> findByTmdbIdFetchJoin(Integer tmdbId, Locale locale);

}
