package com.recody.recodybackend.record.features.addrecord;

import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import com.recody.recodybackend.record.data.rating.RecordRatingEntity;
import com.recody.recodybackend.record.data.rating.RecordRatingRepository;
import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordMapper;
import com.recody.recodybackend.record.data.record.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordAlreadyExists;
import com.recody.recodybackend.record.exceptions.UserNotRatedOnContentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddRecordHandler implements AddRecordHandler{
    
    private final RecordRepository recordRepository;
    
    private final RecordContentRepository contentRepository;
    
    private final RecordMapper recordMapper;
    
    private final RecordRatingRepository recordRatingRepository;
    @Override
    @Transactional
    public String handle(AddRecord command) {
        log.debug("handling command: {}", command);
        String contentId = command.getContentId();
        Long userId = command.getUserId();
        
        RecordContentEntity contentEntity = contentRepository.findByContentId(contentId)
                                                             .orElseThrow(ContentNotFoundException::new);
        
        // 별점이 있는지 체크
        RecordRatingEntity ratingEntity =
                recordRatingRepository.findByUserIdAndContent(userId, contentEntity)
                                      .orElseThrow(UserNotRatedOnContentException::new);
        
        boolean exists = recordRepository.existsByUserIdAndContent(userId, contentEntity);
        if (exists){
            throw new RecordAlreadyExists();
        }
        
        RecordEntity recordEntity = recordMapper.map(command);
        RecordEntity savedRecord = recordRepository.save(recordEntity);
        return savedRecord.getRecordId();
    }
}
