package com.recody.recodybackend.record.data;

import com.recody.recodybackend.record.Record;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    
    Record map(RecordEntity entity);
}
