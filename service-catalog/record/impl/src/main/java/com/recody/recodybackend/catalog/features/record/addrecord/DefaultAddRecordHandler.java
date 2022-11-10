package com.recody.recodybackend.catalog.features.record.addrecord;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.rating.RatingEntity;
import com.recody.recodybackend.catalog.data.rating.RatingRepository;
import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.catalog.data.record.RecordMapper;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.exceptions.RecordAlreadyExists;
import com.recody.recodybackend.exceptions.UserNotRatedOnContentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddRecordHandler implements AddRecordHandler{
    
    private final RecordRepository recordRepository;
    
    private final CatalogContentRepository contentRepository;
    
    private final RecordMapper recordMapper;
    
    private final RatingRepository recordRatingRepository;
    @Override
    @Transactional
    public String handle(AddRecord command) {
        log.debug("handling command: {}", command);
        String contentId = command.getContentId();
        Long userId = command.getUserId();
        
        CatalogContentEntity contentEntity = contentRepository.findByContentId( contentId )
                                                              .orElseThrow(ContentNotFoundException::new);
        
        // 별점이 있는지 체크
        RatingEntity ratingEntity =
                recordRatingRepository.findByUserIdAndContent(userId, contentEntity)
                                      .orElseThrow(UserNotRatedOnContentException::new);
        
        boolean exists = recordRepository.existsByUserIdAndContent(userId, contentEntity);
        if (exists){
            throw new RecordAlreadyExists();
        }
        RecordEntity recordEntity = RecordEntity.builder()
                                         .content( contentEntity )
                                         .title( command.getTitle() )
                                         .note( command.getNote() )
                                         .appreciationDate( command.getAppreciationDate() )
                                         .userId( userId )
                                         .build();
        RecordEntity savedRecord = recordRepository.save(recordEntity);
        return savedRecord.getRecordId();
    }
}
