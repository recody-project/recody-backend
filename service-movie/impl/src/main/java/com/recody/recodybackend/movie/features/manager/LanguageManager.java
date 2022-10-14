package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.spokenlanguage.LanguageEntity;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;

public interface LanguageManager {
    
    LanguageEntity register(SpokenLanguage spokenLanguage);
    
}
