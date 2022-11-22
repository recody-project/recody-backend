package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.contents.register.AsyncContentRegistrar;
import com.recody.recodybackend.movie.Movie;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public interface MovieRegistrar<T> extends AsyncContentRegistrar<Movie, T> {
    
    
    @Override
    @Transactional
    Movie register(T source, Locale locale);
    
    @Override
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    default CompletableFuture<Movie> registerAsync(T t, Locale locale) {
        return AsyncContentRegistrar.super.registerAsync( t, locale );
    }
    @Override
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    default CompletableFuture<List<Movie>> registerAsync(List<T> source, Locale locale) {
        return AsyncContentRegistrar.super.registerAsync( source, locale );
    }
}
