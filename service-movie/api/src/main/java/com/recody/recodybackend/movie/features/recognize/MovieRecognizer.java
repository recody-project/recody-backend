package com.recody.recodybackend.movie.features.recognize;

import com.recody.recodybackend.movie.Movie;


public interface MovieRecognizer {
    
    String recognize(Movie movie);
}
