package com.recody.recodybackend.movie.features.getmoviecredit;

import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        imports = {
        TMDB.class
        })
abstract class ActorMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tmdbId", source = "cast.id")
    @Mapping(target = "profilePath", expression = "java(TMDB.fullPosterPath(cast.getProfilePath()))")
    public abstract Actor map(TMDBCast cast);
    
}
