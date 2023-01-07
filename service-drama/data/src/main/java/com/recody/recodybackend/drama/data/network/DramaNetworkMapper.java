package com.recody.recodybackend.drama.data.network;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaNetwork;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class DramaNetworkMapper {
    
    @Mapping( target = "tmdbId", source = "network.id" )
    @Mapping( target = "id", ignore = true)
    public abstract DramaNetworkInfoEntity newEntity(TMDBDramaNetwork network);
    
    public abstract List<DramaNetworkInfoEntity> newEntity(List<TMDBDramaNetwork> networks);
    
    @Mapping( target = "id", ignore = true)
    public abstract DramaNetworkEntity newEntity(DramaEntity drama, DramaNetworkInfoEntity networkInfo);
    
    // Entity -> String
    public String mapByLocale(List<DramaNetworkEntity> entities, @Context Locale locale) {
        StringBuilder stringBuilder = new StringBuilder();
        if ( !entities.isEmpty() ) {
            for (DramaNetworkEntity entity : entities) {
                concatNetworkNameByLocale( locale, stringBuilder, entity.getNetworkInfo() );
            }
        }
        return stringBuilder.toString();
    }
    
    private void concatNetworkNameByLocale(Locale locale, StringBuilder stringBuilder,
                                           DramaNetworkInfoEntity networkInfo) {
        // 현재 방송사는 국제화하여 취급하지 않는다.
        if ( networkInfo == null ) {
            return;
        }
        String name = networkInfo.getName();
        if ( StringUtils.hasText( name )){
            stringBuilder.append( name );
        }
    }
    
}
