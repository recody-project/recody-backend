package com.recody.recodybackend.movie.features.recognize;

import com.recody.recodybackend.movie.general.MovieGenre;

public interface MovieGenreRecognizer {
    
    String recognize(MovieGenre genre);
    
}