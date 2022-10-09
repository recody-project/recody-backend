package com.recody.recodybackend.record.features.getrecordcontents;

import com.recody.recodybackend.record.RecordContent;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import com.recody.recodybackend.record.data.record.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class DefaultGetRecordContentsHandler implements GetRecordContentsHandler{
    
    private final RecordRepository recordRepository;
    private final RecordContentRepository contentRepository;
    
    @Override
    public List<RecordContent> handle(GetRecordContents command) {
        
        return null;
    }
}
