package com.recody.recodybackend.movie.data.spokenlanguage;

import com.recody.recodybackend.common.data.AsyncEntityRegistrar;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;

public interface LanguageManager extends AsyncEntityRegistrar<LanguageEntity, SpokenLanguage> {
    
    LanguageEntity register(SpokenLanguage spokenLanguage);
}
