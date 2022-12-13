package com.recody.recodybackend.movie.features.fetchmoviecredit;

import java.util.concurrent.CompletableFuture;

public interface GetMovieCreditHandler {
    
    GetMovieCreditResult handle(GetMovieCredit command) ;
    CompletableFuture<GetMovieCreditResult> handleAsync(GetMovieCredit command);

}
