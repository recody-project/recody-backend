package com.recody.recodybackend.drama.data.drama;

import com.recody.recodybackend.drama.Drama;
import com.recody.recodybackend.drama.DramaDetail;
import com.recody.recodybackend.drama.data.network.DramaNetworkMapper;
import com.recody.recodybackend.drama.data.overview.DramaOverviewMapper;
import com.recody.recodybackend.drama.data.people.DramaPersonMapper;
import com.recody.recodybackend.drama.data.title.DramaTitleMapper;
import com.recody.recodybackend.drama.tmdb.TMDB;
import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaDetail;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;

@Mapper( componentModel = "spring",
         uses = {DramaTitleMapper.class, DramaOverviewMapper.class, DramaPersonMapper.class, DramaNetworkMapper.class},
         imports = {TMDB.class},
         builder = @Builder(disableBuilder = true))
public abstract class DramaMapper {
    
    @Autowired
    DramaTitleMapper titleMapper;
    
    @Mapping( target = "title", source = "entity.title")
    public abstract Drama map(DramaEntity entity, @Context Locale locale);
    public abstract List<Drama> map(List<DramaEntity> entities, @Context Locale locale);
    
    @Mapping( target = "overview", ignore = true )
    @Mapping( target = "networks", ignore = true )
    @Mapping( target = "directors", ignore = true )
    @Mapping( target = "actors", ignore = true )
    @Mapping( target = "numberOfEpisodes", ignore = true )
    @Mapping( target = "firstAirDate", ignore = true )
    @Mapping( target = "tmdbId", source = "drama.id" )
    @Mapping( target = "title", source = "drama" )
    @Mapping( target = "imageUrl", expression = "java(TMDB.fullPosterPath(drama.getPosterPath()))" )
    @Mapping( target = "id", ignore = true )
    public abstract DramaEntity newEntity(TMDBDrama drama, @Context Locale locale);
    
    public abstract List<DramaEntity> newEntity(List<TMDBDrama> dramas, @Context Locale locale);
    
    @Mapping( target = "overview", ignore = true )
    @Mapping( target = "networks", ignore = true )
    @Mapping( target = "directors", ignore = true )
    @Mapping( target = "actors", ignore = true )
    @Mapping( target = "numberOfEpisodes", ignore = true )
    @Mapping( target = "firstAirDate", ignore = true )
    @Mapping( target = "tmdbId", ignore = true )
    @Mapping( target = "title", expression = "java(titleMapper.update( entity.getTitle(), drama, locale ))" )
    @Mapping( target = "imageUrl", expression = "java(TMDB.fullPosterPath(drama.getPosterPath()))" )
    @Mapping( target = "id", ignore = true )
    public abstract void update(@MappingTarget DramaEntity entity, TMDBDrama drama, @Context Locale locale);
    
    
    // detail
    
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "tmdbId", source = "id" )
    @Mapping( target = "title", source = "detail" )
    @Mapping( target = "imageUrl", expression = "java(TMDB.fullPosterPath(detail.getPosterPath()))" )
    @Mapping( target = "directors", ignore = true )
    @Mapping( target = "actors", ignore = true )
    public abstract DramaEntity newEntity(TMDBDramaDetail detail, @Context Locale locale);
    
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "tmdbId", source = "id" )
    @Mapping( target = "title", source = "detail" )
    @Mapping( target = "directors", ignore = true)
    @Mapping( target = "imageUrl", expression = "java(TMDB.fullPosterPath(detail.getPosterPath()))" )
    @Mapping( target = "actors", ignore = true )
    @Mapping( target = "overview", source = "detail.overview")
    public abstract void updateDetail(@MappingTarget DramaEntity entity,
                                      TMDBDramaDetail detail,
                                      @Context Locale locale);
    
    // DramaDetail 로 만들어 반환하기
    public abstract DramaDetail toDramaDetail(DramaEntity entity, @Context Locale locale);
}
