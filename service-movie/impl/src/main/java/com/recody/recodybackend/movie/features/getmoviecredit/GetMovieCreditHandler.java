package com.recody.recodybackend.movie.features.getmoviecredit;

import java.util.concurrent.CompletableFuture;

public interface GetMovieCreditHandler {
    
    GetMovieCreditResult handle(GetMovieCredit command) ;
    CompletableFuture<GetMovieCreditResult> handleAsync(GetMovieCredit command);

}
