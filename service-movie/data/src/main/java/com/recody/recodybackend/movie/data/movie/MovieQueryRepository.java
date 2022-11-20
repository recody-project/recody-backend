package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.common.contents.GenreIds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface MovieQueryRepository {
    
    List<MovieEntity> findByTitleLike(String title, Locale locale, Pageable pageable);
    
    Page<MovieEntity> findPagedByTitleLike(String title, Locale locale, Pageable pageable);
    
    Page<MovieEntity> findPagedByTitleLikeFilterByGenreIds(String title, Locale locale, Pageable pageable, GenreIds genreIds);
    Optional<MovieEntity> findByTmdbIdFetchJoin(Integer tmdbId, Locale locale);

}
