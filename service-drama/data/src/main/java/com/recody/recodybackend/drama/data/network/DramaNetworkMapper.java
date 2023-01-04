package com.recody.recodybackend.drama.data.network;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaNetwork;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class DramaNetworkMapper {
    
    @Mapping( target = "tmdbId", source = "network.id" )
    @Mapping( target = "id", ignore = true)
    public abstract DramaNetworkInfoEntity newEntity(TMDBDramaNetwork network);
    
    public abstract List<DramaNetworkInfoEntity> newEntity(List<TMDBDramaNetwork> networks);
    
    @Mapping( target = "id", ignore = true)
    public abstract DramaNetworkEntity newEntity(DramaEntity drama, DramaNetworkInfoEntity networkInfo);
    
}
