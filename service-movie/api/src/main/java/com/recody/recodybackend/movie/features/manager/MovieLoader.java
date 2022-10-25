package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.common.contents.register.ContentLoader;
import com.recody.recodybackend.common.contents.movie.Movie;

import java.util.Locale;
import java.util.Optional;

/**
 * @param <ID> 사용하고자하는 API 의 ID.
 * @author motive
 */
public interface MovieLoader<ID> extends ContentLoader<Movie, ID> {
    
    @Override
    Optional<Movie> load(ID sourceIdentifier, Locale locale);
    
}
