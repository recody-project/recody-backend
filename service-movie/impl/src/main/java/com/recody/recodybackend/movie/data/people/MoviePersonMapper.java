package com.recody.recodybackend.movie.data.people;


import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;
import com.recody.recodybackend.movie.features.getmoviecredit.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class MoviePersonMapper {
    
    
    @Mapping(target = "profilePath", source = "entity.person.profilePath")
    @Mapping(target = "character", source = "entity.character")
    @Mapping(target = "id", source = "entity.person.id")
    @Mapping(target = "tmdbId", source = "entity.person.tmdbId")
    @Mapping(target = "name", source = "entity.person.name.englishName")
    public abstract Actor map(MovieActorEntity entity);
    @Mapping(target = "person", source = "actor", qualifiedByName = "mapsActorToPerson")
    @Mapping(target = "movie", source = "movie")
    @Mapping(target = "id", ignore = true)
    public abstract MovieActorEntity newActorEntity(Actor actor, MovieEntity movie);
    
    @Mapping(target = "person", source = "director", qualifiedByName = "mapsDirectorToPerson")
    @Mapping(target = "movie", source = "movie")
    @Mapping(target = "id", ignore = true)
    public abstract MovieDirectorEntity newDirectorEntity(Director director, MovieEntity movie);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "actor.name", qualifiedByName = "mapsName")
    @Named("mapsActorToPerson")
    public abstract MoviePersonEntity newPersonEntity(Actor actor);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "director.name", qualifiedByName = "mapsName")
    @Named("mapsDirectorToPerson")
    public abstract MoviePersonEntity newPersonEntity(Director director);
    
    @Named("mapsName")
    public MoviePersonNameEntity map(String name) {
        // 현재 영어이름만 받을 수 있어서 조건 없이 영어이름으로 매핑.
        return MoviePersonNameEntity.builder()
                                    .englishName(name)
                                    .build();
    }
}
