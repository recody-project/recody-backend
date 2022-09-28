package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.features.getmoviedetail.SpokenLanguage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpokenLanguageMapper {
    
    @Mapping(target = "id", source = "iso_639_1")
    LanguageEntity toEntity(SpokenLanguage spokenLanguage);
    
}
