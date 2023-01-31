package com.recody.recodybackend.drama.data.people;

import com.recody.recodybackend.drama.tmdb.TMDB;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCast;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCrew;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

@Mapper( componentModel = "spring",
         uses = {
            DramaPersonNameMapper.class
         },
         imports = {TMDB.class},
         builder = @Builder(disableBuilder = true) )
@Slf4j
public abstract class DramaPersonMapper {
    
    @Autowired
    private DramaPersonNameMapper personNameMapper;
    
    @Mapping( target = "tmdbId", source = "cast.id" )
    @Mapping( target = "profileUrl", expression = "java(TMDB.fullPosterPath(cast.getProfilePath()))" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "name", source = "cast" )
    public abstract DramaPersonEntity newEntity(TMDBDramaCast cast, @Context Locale locale);
    
    public abstract List<DramaPersonEntity> newEntityFromCasts(List<TMDBDramaCast> casts, @Context Locale locale);
    
    @Mapping( target = "tmdbId", source = "crew.id" )
    @Mapping( target = "profileUrl", expression = "java(TMDB.fullPosterPath(crew.getProfilePath()))" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "name", source = "crew" )
    public abstract DramaPersonEntity newEntity(TMDBDramaCrew crew, @Context Locale locale);
    
    public abstract List<DramaPersonEntity> newEntityFromCrews(List<TMDBDramaCrew> crews, @Context Locale locale);
    
    public String concatActors(List<DramaActorEntity> entities, @Context Locale locale) {
        StringBuilder stringBuilder = new StringBuilder();
        if ( !entities.isEmpty() ) {
            for (DramaActorEntity actor : entities) {
                concatPersonNameByLocale( locale, stringBuilder, actor.getPerson() );
            }
        }
        return stringBuilder.toString();
    }
    
    public String concatDirectors(List<DramaDirectorEntity> entities, @Context Locale locale) {
        StringBuilder stringBuilder = new StringBuilder();
        if ( !entities.isEmpty() ) {
            for (DramaDirectorEntity director : entities) {
                concatPersonNameByLocale( locale, stringBuilder, director.getPerson() );
            }
        }
        return stringBuilder.toString();
    }
    
    private void concatPersonNameByLocale(@Context Locale locale,
                                          StringBuilder stringBuilder,
                                          DramaPersonEntity person) {
        String name = personNameMapper.map( person.getName(), locale );
        if ( !StringUtils.hasText( name ) ){
            log.warn( "이 Person 에 Name 정보가 없습니다." );
            return;
        }
        
        if ( stringBuilder.length() != 0 ) {
            stringBuilder.append( ", " );
        }
        
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            stringBuilder.append( name );
        }
        else {
            stringBuilder.append( name );
            
        }
    }
}
