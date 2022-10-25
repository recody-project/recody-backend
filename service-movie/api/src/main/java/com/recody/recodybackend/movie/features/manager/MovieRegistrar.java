package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.common.contents.register.AsyncContentRegistrar;
import com.recody.recodybackend.common.contents.movie.Movie;

import javax.transaction.Transactional;
import java.util.Locale;

public interface MovieRegistrar<T> extends AsyncContentRegistrar<Movie, T> {
    
    
    @Override
    @Transactional
    Movie register(T source, Locale locale);
}
