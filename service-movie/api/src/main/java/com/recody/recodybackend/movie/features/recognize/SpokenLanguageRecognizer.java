package com.recody.recodybackend.movie.features.recognize;

import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;

public interface SpokenLanguageRecognizer {
    
    String recognize(SpokenLanguage spokenLanguage);
    
}
