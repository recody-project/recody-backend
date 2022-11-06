package com.recody.recodybackend.catalog.data.record;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.record.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RecordMapper {
    
    @Autowired
    CatalogContentRepository contentRepository;
    
    @Mapping(target = "contentId", source = "entity.content.contentId")
    public abstract Record map(RecordEntity entity);
    
    @Named("contentMapper")
    public CatalogContentEntity map(String contentId) {
        return contentRepository.findByContentId(contentId).orElseThrow(ContentNotFoundException::new);
    }
}
