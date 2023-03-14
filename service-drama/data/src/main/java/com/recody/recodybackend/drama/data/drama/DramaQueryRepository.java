package com.recody.recodybackend.drama.data.drama;

import com.recody.recodybackend.common.contents.GenreIds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;

public interface DramaQueryRepository {
    
    List<DramaEntity> findByTitleLike(String keyword, Locale locale, Pageable pageable);
    Page<DramaEntity> findPagedByTitleLike(String keyword, Locale locale, Pageable pageable);
    
    List<DramaEntity> findByTitleLikeFilterByGenres(String keyword, Locale locale, Pageable pageable, GenreIds genreIds);
    Page<DramaEntity> findPagedByTitleLikeFilterByGenres(String keyword, Locale locale, Pageable pageable, GenreIds genreIds);
    
    
    
}
