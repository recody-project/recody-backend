package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.spokenlanguage.LanguageEntity;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;

import java.util.concurrent.CompletableFuture;

public interface LanguageManager {
    
    LanguageEntity register(SpokenLanguage spokenLanguage);
    CompletableFuture<LanguageEntity> registerAsync(SpokenLanguage spokenLanguage);
    
}
