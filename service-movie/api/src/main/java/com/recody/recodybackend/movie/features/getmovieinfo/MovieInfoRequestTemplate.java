package com.recody.recodybackend.movie.features.getmovieinfo;

public interface MovieInfoRequestTemplate {
    
    String executeToString(MovieInfoRequestEntity request);
    MovieInfoResponse executeToJson(MovieInfoRequestEntity request);
}
