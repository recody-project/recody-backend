package com.recody.recodybackend.movie.features.fetchmoviecredit;

import com.recody.recodybackend.common.CommandHandler;
import com.recody.recodybackend.common.Recody;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface FetchMovieCreditsHandler<T, S> extends CommandHandler<T, S> {
    
    T handle(S command);
    
    @Override
    @Async(value = Recody.MOVIE_TASK_EXECUTOR )
    default CompletableFuture<T> handleAsync(S s) {
        return CommandHandler.super.handleAsync( s );
    }
}
