package com.recody.recodybackend.drama.data.people;

import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCast;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCrew;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Locale;

@Mapper( componentModel = "spring",
         uses = {
            DramaPersonNameMapper.class
         },
         builder = @Builder(disableBuilder = true) )
@Slf4j
public abstract class DramaPersonMapper {
    
    @Mapping( target = "tmdbId", source = "cast.id" )
    @Mapping( target = "profileUrl", source = "cast.profilePath" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "name", source = "cast" )
    public abstract DramaPersonEntity newEntity(TMDBDramaCast cast, @Context Locale locale);
    
    public abstract List<DramaPersonEntity> newEntityFromCasts(List<TMDBDramaCast> casts, @Context Locale locale);
    
    @Mapping( target = "tmdbId", source = "crew.id" )
    @Mapping( target = "profileUrl", source = "crew.profilePath" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "name", source = "crew" )
    public abstract DramaPersonEntity newEntity(TMDBDramaCrew crew, @Context Locale locale);
    
    public abstract List<DramaPersonEntity> newEntityFromCrews(List<TMDBDramaCrew> crews, @Context Locale locale);
}
