package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.people.MoviePersonEntity;
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;
import com.recody.recodybackend.movie.features.getmoviecredit.Director;

import java.util.concurrent.CompletableFuture;

public interface PersonManager {
    
    MoviePersonEntity register(Actor actor);
    
    CompletableFuture<MoviePersonEntity> registerAsync(Actor actor);
    
    MoviePersonEntity register(Director director);
    
    CompletableFuture<MoviePersonEntity> registerAsync(Director director);
    
}
