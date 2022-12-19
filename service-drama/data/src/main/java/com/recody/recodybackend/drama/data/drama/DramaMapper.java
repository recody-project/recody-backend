package com.recody.recodybackend.drama.data.drama;

import com.recody.recodybackend.drama.Drama;
import com.recody.recodybackend.drama.data.title.DramaTitleMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Locale;

@Mapper( componentModel = "spring",
         uses = {DramaTitleMapper.class} )
public abstract class DramaMapper {
    
    @Mapping( target = "title", source = "entity.title")
    public abstract Drama map(DramaEntity entity, @Context Locale locale);
    public abstract List<Drama> map(List<DramaEntity> entities, @Context Locale locale);
}
