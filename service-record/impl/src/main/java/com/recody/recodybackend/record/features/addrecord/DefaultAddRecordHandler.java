package com.recody.recodybackend.record.features.addrecord;

import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordMapper;
import com.recody.recodybackend.record.data.record.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordAlreadyExists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddRecordHandler implements AddRecordHandler{
    
    private final RecordRepository recordRepository;
    
    private final RecordContentRepository contentRepository;
    
    private final RecordMapper recordMapper;
    
    @Override
    @Transactional
    public String handle(AddRecord command) {
        log.debug("handling command: {}", command);
        String contentId = command.getContentId();
        Long userId = command.getUserId();
        Optional<RecordContentEntity> optionalContent = contentRepository.findByContentId(contentId);
        
        if (optionalContent.isEmpty()){
            throw new ContentNotFoundException();
        }
        
        Optional<List<RecordEntity>> optionalRecords = recordRepository.findAllByUserIdAndContent(userId,
                                                                                                  optionalContent.get());
        
        if (optionalRecords.isPresent()) {
            if (!optionalRecords.get().isEmpty()) {
                throw new RecordAlreadyExists();
            }
        }
        
        RecordEntity recordEntity = recordMapper.map(command);
        RecordEntity savedRecord = recordRepository.save(recordEntity);
        return savedRecord.getRecordId();
    }
}
