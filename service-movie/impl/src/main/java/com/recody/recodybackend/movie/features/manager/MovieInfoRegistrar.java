package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.common.contents.register.AsyncContentInfoRegistrar;
import com.recody.recodybackend.movie.Movie;

import java.util.Locale;

/**
 * @param <TARGET> 등록 후 받고자하는 도메인 객체
 * @param <SOURCE> 데이터 소스가 되는 DTO
 * @author motive
 */
public interface MovieInfoRegistrar<TARGET, SOURCE> extends AsyncContentInfoRegistrar<Movie, TARGET, SOURCE> {
    
    TARGET register(Movie movie, SOURCE source, Locale locale);
    
}
