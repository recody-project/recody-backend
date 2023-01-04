package com.recody.recodybackend.catalog.features.record.getrecordcontent;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentMapper;
import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.catalog.data.record.RecordMapper;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.record.RecordContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetContinuingRecordContentHandler implements GetContinuingRecordContentHandler {
    
    private final RecordRepository recordRepository;
    private final CatalogContentMapper contentMapper;
    
    private final RecordMapper recordMapper;
    
    @Override
    public RecordContent handle(GetContinuingRecordContent command) {
        log.debug( "command: {}", command );
        Locale locale = command.getLocale();
        Long userId = command.getUserId();
        Optional<RecordEntity> optionalRecord
                = recordRepository.findFirstByUserIdAndCompletedIsFalseOrderByLastModifiedAtDesc( userId );
        if ( optionalRecord.isEmpty() ) {
            return null;
        }
        RecordEntity recordEntity = optionalRecord.get();
        CatalogContentEntity contentEntity = recordEntity.getContent();
        RecordContent content = recordMapper.map( contentEntity, recordEntity, locale );
        log.info( "found most recent continuing content: {}", content );
        return content;
    }
}
