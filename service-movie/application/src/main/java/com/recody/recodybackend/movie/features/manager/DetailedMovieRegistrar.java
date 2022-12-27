package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.contents.register.AsyncContentRegistrar;
import com.recody.recodybackend.movie.MovieInfo;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public interface DetailedMovieRegistrar<T> extends AsyncContentRegistrar<MovieInfo,T> {
    
    @Override
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    @Transactional
    default CompletableFuture<MovieInfo> registerAsync(T t, Locale locale) {
        return AsyncContentRegistrar.super.registerAsync( t, locale );
    }
    @Override
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    @Transactional
    default CompletableFuture<List<MovieInfo>> registerAsync(List<T> source, Locale locale) {
        return AsyncContentRegistrar.super.registerAsync( source, locale );
    }
}
