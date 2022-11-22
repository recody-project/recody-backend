package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.contents.register.AsyncContentInfoRegistrar;
import com.recody.recodybackend.movie.Movie;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

/**
 * @param <TARGET> 등록 후 받고자하는 도메인 객체
 * @param <SOURCE> 데이터 소스가 되는 DTO
 * @author motive
 */
public interface MovieInfoRegistrar<TARGET, SOURCE> extends AsyncContentInfoRegistrar<Movie, TARGET, SOURCE> {
    
    TARGET register(Movie movie, SOURCE source, Locale locale);
    
    @Override
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    default CompletableFuture<List<TARGET>> registerAsync(Movie content, List<SOURCE> sources, Locale locale) {
        return AsyncContentInfoRegistrar.super.registerAsync( content, sources, locale );
    }
    @Override
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    default CompletableFuture<TARGET> registerAsync(Movie content, SOURCE source, Locale locale) {
        return AsyncContentInfoRegistrar.super.registerAsync( content, source, locale );
    }
}
