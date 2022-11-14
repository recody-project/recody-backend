package com.recody.recodybackend.catalog.data.user;

import com.recody.recodybackend.users.events.UserCreated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CatalogUserMapper {
    
    @Mapping( target = "id", source = "event.userId" )
    public abstract CatalogUserEntity newEntity(UserCreated event);
    public abstract List<CatalogUserEntity> newEntity(List<UserCreated> event);
    
}
