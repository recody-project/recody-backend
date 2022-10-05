package com.recody.recodybackend.record.data;

import com.recody.recodybackend.common.events.ContentCreated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    
    @Mapping(target = "id", source = "event.catalogId")
    RecordContentEntity map(ContentCreated event);
}
