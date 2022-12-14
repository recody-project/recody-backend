package com.recody.recodybackend.movie.data.people;


import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import org.mapstruct.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {TMDB.class}, uses = {MoviePersonNameMapper.class})
public abstract class MoviePersonMapper {
    
    @Named("fullProfilePath")
    public String makeFullProfilePath(String newPosterPath){
        return TMDB.fullPosterPath(newPosterPath);
    }
    
    public List<Actor> toActor(List<TMDBCast> casts) {
        return casts.stream()
                    .filter(it -> it.getKnownForDepartment().equals(TMDB.ACTING))
                    .map(this::toActor)
                    .collect(Collectors.toList());
    }
    
    public List<Director> toDirector(List<TMDBCrew> crews) {
        return crews.stream()
                    .filter(crew -> crew.getJob().equals(TMDB.DIRECTOR))
                    .limit(TMDB.DIRECTOR_MAX_SIZE)
                    .map(this::toDirector)
                    .collect(Collectors.toList());
    }
    
    @Mapping(target = "profilePath", source = "entity.person.profilePath")
    @Mapping(target = "character", source = "entity.character")
    @Mapping(target = "id", source = "entity.person.id")
    @Mapping(target = "name", source = "entity.person.name")
    public abstract Actor toActor(MovieActorEntity entity, @Context Locale locale);
    
    @Mapping(target = "profilePath", expression = "java(TMDB.fullPosterPath(cast.getProfilePath()))")
    @Mapping(target = "character", source = "cast.character")
    @Mapping(target = "id", source = "cast.id")
    @Mapping(target = "name", source = "cast.name")
    public abstract Actor toActor(TMDBCast cast);
    
    @Mapping(target = "profilePath", source = "entity.person.profilePath")
    @Mapping(target = "id", source = "entity.person.id")
    @Mapping(target = "name", source = "entity.person.name")
    public abstract Director toDirector(MovieDirectorEntity entity, @Context Locale locale);
    
    @Mapping(target = "profilePath", expression = "java(TMDB.fullPosterPath(crew.getProfilePath()))")
    @Mapping(target = "id", source = "crew.id")
    @Mapping(target = "name", source = "crew.name")
    public abstract Director toDirector(TMDBCrew crew);
    
    @Mapping(target = "tmdbId", source = "cast.id")
    @Mapping(target = "name", source = "cast.name")
    @Mapping(target = "profilePath",
             source = "cast.profilePath",
             qualifiedByName = "fullProfilePath",
             conditionExpression = "java(cast.getProfilePath() != null)")
    public abstract MoviePersonEntity newPersonEntity(TMDBCast cast);
    
    @Mapping(target = "tmdbId", source = "crew.id")
    @Mapping(target = "name", source = "crew.name")
    @Mapping(target = "profilePath",
             source = "crew.profilePath",
             qualifiedByName = "fullProfilePath",
             conditionExpression = "java(crew.getProfilePath() != null)")
    public abstract MoviePersonEntity newPersonEntity(TMDBCrew crew);
    
    
    @Mapping(target = "profilePath",
             source = "newPerson.profilePath",
             qualifiedByName = "fullProfilePath",
             conditionExpression = "java(newPerson.getProfilePath() != null)")
    public abstract MoviePersonEntity update(@MappingTarget MoviePersonEntity currentPerson,
                                             MoviePersonEntity newPerson);
    
    @Mapping(target = "id", source = "entity.id", conditionExpression = "java(entity.getId() != null)")
    public abstract MoviePersonEntity map(MoviePersonEntity entity);
    
}
