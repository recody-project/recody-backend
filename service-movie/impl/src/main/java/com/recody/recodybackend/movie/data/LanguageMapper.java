package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.features.getmoviedetail.SpokenLanguage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    
    LanguageEntity map(SpokenLanguage language);
    
}
