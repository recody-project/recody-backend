package com.recody.recodybackend.movie.features.getmoviecredit;

import com.recody.recodybackend.movie.data.people.MovieDirectorEntity;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = {
        TMDB.class
})
@Slf4j
public abstract class DirectorMapper {
    
    @Mapping(target = "id", source = "crew.id")
    @Mapping(target = "profilePath", expression = "java(TMDB.fullPosterPath(crew.getProfilePath()))")
    public abstract Director map(TMDBCrew crew);
    
    @Mapping(target = "movie",ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract MovieDirectorEntity update(@MappingTarget MovieDirectorEntity currentEntity,
                                               MovieDirectorEntity nextEntity);
}
