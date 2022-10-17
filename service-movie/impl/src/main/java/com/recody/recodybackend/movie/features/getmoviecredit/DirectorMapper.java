package com.recody.recodybackend.movie.features.getmoviecredit;

import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {
        TMDB.class
})
abstract class DirectorMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tmdbId", source = "crew.id")
    @Mapping(target = "profilePath", expression = "java(TMDB.fullPosterPath(crew.getProfilePath()))")
    public abstract Director map(TMDBCrew crew);
    
}
