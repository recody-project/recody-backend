package com.recody.recodybackend.movie.data.movie;

import java.util.List;
import java.util.Locale;

public interface MovieQueryRepository {
    
    List<MovieEntity> findByTitleLike(String title, Locale locale);

}
