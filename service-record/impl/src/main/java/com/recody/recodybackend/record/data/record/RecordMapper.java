package com.recody.recodybackend.record.data.record;

import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import com.recody.recodybackend.record.features.addrecord.AddRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RecordMapper {
    
    @Autowired
    RecordContentRepository contentRepository;
    
    @Mapping(target = "contentId", source = "entity.content.contentId")
    public abstract Record map(RecordEntity entity);
    
    @Mapping(target = "recordId", ignore = true)
    @Mapping(target = "nth", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "content", source = "contentId", qualifiedByName = "contentMapper")
    public abstract RecordEntity map(AddRecord command);
    
    @Named("contentMapper")
    public RecordContentEntity map(String contentId) {
        return contentRepository.findByContentId(contentId).orElseThrow(ContentNotFoundException::new);
    }
}
