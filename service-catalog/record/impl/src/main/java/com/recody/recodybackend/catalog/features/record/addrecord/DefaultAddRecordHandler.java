package com.recody.recodybackend.catalog.features.record.addrecord;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.rating.RatingEntity;
import com.recody.recodybackend.catalog.data.rating.RatingRepository;
import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.catalog.data.record.RecordMapper;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.exceptions.RecordAlreadyExists;
import com.recody.recodybackend.exceptions.UserNotRatedOnContentException;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddRecordHandler implements AddRecordHandler {
    
    private final RecordRepository recordRepository;
    private final CatalogContentRepository contentRepository;
    
    private final RecordMapper recordMapper;
    private final CatalogUserRepository userRepository;
    private final RatingRepository ratingRepository;
    
    @Override
    @Transactional
    public String handle(AddRecord command) {
        log.debug( "handling command: {}", command );
        String contentId = command.getContentId();
        Long userId = command.getUserId();
        CatalogUserEntity catalogUserEntity = userRepository.findById( userId ).orElseThrow( UserNotFoundException::new );
        
        CatalogContentEntity contentEntity = contentRepository.findByContentId( contentId )
                                                              .orElseThrow( ContentNotFoundException::new );
        
        // 별점이 있는지 체크
        throwIfNotRated( userId, contentEntity );
        Integer appreciationNumber = command.getAppreciationNumber().getValue();
        
        boolean exists = recordRepository.existsByUserAndContentAndAppreciationNumber( catalogUserEntity, contentEntity, appreciationNumber );
        if ( exists ) {
            throw new RecordAlreadyExists();
        }
        RecordEntity recordEntity = RecordEntity.builder()
                                                .content( contentEntity )
                                                .title( command.getTitle() )
                                                .note( command.getNote() )
                                                .appreciationDate( command.getAppreciationDate() )
                                                .user( catalogUserEntity )
                                                .appreciationNumber( appreciationNumber )
                                                .build();
        RecordEntity savedRecord = recordRepository.save( recordEntity );
        return savedRecord.getRecordId();
    }
    
    private void throwIfNotRated(Long userId, CatalogContentEntity contentEntity) {
        RatingEntity ratingEntity =
                ratingRepository.findByUserIdAndContent( userId, contentEntity )
                                .orElseThrow( UserNotRatedOnContentException::new );
    }
}
