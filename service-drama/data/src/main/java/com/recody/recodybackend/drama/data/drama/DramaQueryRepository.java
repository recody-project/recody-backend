package com.recody.recodybackend.drama.data.drama;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;

public interface DramaQueryRepository {
    
    List<DramaEntity> findByTitleLike(String keyword, Locale locale, Pageable pageable);
    
}
