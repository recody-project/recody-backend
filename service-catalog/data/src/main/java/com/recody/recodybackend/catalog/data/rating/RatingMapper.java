package com.recody.recodybackend.catalog.data.rating;

import com.recody.recodybackend.rating.RatingScore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RatingMapper {
    
    @Mapping( target = "value", source = "entity.score" )
    public abstract RatingScore map(RatingEntity entity);
    
}
