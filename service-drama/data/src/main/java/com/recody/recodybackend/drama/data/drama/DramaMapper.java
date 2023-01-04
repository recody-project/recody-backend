package com.recody.recodybackend.drama.data.drama;

import com.recody.recodybackend.drama.Drama;
import com.recody.recodybackend.drama.data.title.DramaTitleMapper;
import com.recody.recodybackend.drama.tmdb.TMDB;
import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;

@Mapper( componentModel = "spring",
         uses = {DramaTitleMapper.class},
         imports = {TMDB.class},
         builder = @Builder(disableBuilder = true))
public abstract class DramaMapper {
    
    @Autowired
    DramaTitleMapper titleMapper;
    
    @Mapping( target = "title", source = "entity.title")
    public abstract Drama map(DramaEntity entity, @Context Locale locale);
    public abstract List<Drama> map(List<DramaEntity> entities, @Context Locale locale);
    
    @Mapping( target = "tmdbId", source = "drama.id" )
    @Mapping( target = "title", source = "drama" )
    @Mapping( target = "imageUrl", expression = "java(TMDB.fullPosterPath(drama.getPosterPath()))" )
    @Mapping( target = "id", ignore = true )
    public abstract DramaEntity newEntity(TMDBDrama drama, @Context Locale locale);
    
    public abstract List<DramaEntity> newEntity(List<TMDBDrama> dramas, @Context Locale locale);
    
    @Mapping( target = "tmdbId", ignore = true )
    @Mapping( target = "title", expression = "java(titleMapper.update( entity.getTitle(), drama, locale ))" )
    @Mapping( target = "imageUrl", expression = "java(TMDB.fullPosterPath(drama.getPosterPath()))" )
    @Mapping( target = "id", ignore = true )
    public abstract void update(@MappingTarget DramaEntity entity, TMDBDrama drama, @Context Locale locale);
}
