package com.recody.recodybackend.drama.features.fetchdramagenres;

public interface FetchDramaGenresHandler<R> {
    
    R handle(FetchDramaGenres query);
    
}
