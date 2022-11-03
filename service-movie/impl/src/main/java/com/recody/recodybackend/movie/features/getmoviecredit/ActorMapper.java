package com.recody.recodybackend.movie.features.getmoviecredit;

import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.data.people.MovieActorEntity;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        imports = {
        TMDB.class
        })
@Slf4j
public abstract class ActorMapper {
    
    @Mapping(target = "id", source = "cast.id")
    @Mapping(target = "profilePath", expression = "java(TMDB.fullPosterPath(cast.getProfilePath()))")
    public abstract Actor map(TMDBCast cast);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movie", ignore = true)
    public abstract MovieActorEntity update(@MappingTarget MovieActorEntity currentActor, MovieActorEntity nextActor);
}
