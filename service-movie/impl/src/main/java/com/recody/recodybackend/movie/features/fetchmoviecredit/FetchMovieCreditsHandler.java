package com.recody.recodybackend.movie.features.fetchmoviecredit;

import com.recody.recodybackend.common.CommandHandler;

public interface FetchMovieCreditsHandler<T, S> extends CommandHandler<T, S> {
    
    T handle(S command);
    
}
