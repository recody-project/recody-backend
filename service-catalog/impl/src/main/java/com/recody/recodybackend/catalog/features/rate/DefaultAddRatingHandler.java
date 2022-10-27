package com.recody.recodybackend.catalog.features.rate;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.rating.RatingEntity;
import com.recody.recodybackend.catalog.data.rating.RatingRepository;
import com.recody.recodybackend.catalog.features.projection.ContentEventPublisher;
import com.recody.recodybackend.common.events.ContentRated;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddRatingHandler implements AddRatingHandler {
    private final CatalogContentRepository contentRepository;
    private final RatingRepository ratingRepository;
    private final ContentEventPublisher contentEventPublisher;
    
    @Override
    @Transactional
    public UUID handle(AddRating command) {
        log.debug("handing command: {}", command);
        Long userId = command.getUserId();
        String contentId = command.getContentId().getContentId();
        Integer scoreValue = command.getScore().getValue();
        
        CatalogContentEntity contentEntity
                = contentRepository.findByContentId(contentId)
                                   .orElseThrow(ContentNotFoundException::new);
        
        Optional<RatingEntity> optionalRating
                = ratingRepository.findByUserIdAndContent(userId, contentEntity);
        
        if ( optionalRating.isPresent() ) {
            RatingEntity ratingEntity1 = optionalRating.get();
            ratingEntity1.setScore(scoreValue);
            ContentRated event = createEvent(ratingEntity1);
            contentEventPublisher.publish(event);
            log.debug("평점이 수정되었습니다.: {}", ratingEntity1);
            return event.getEventId();
        }
        
        RatingEntity newRatingEntity = RatingEntity.builder()
                                   .userId(userId)
                                   .content(contentEntity)
                                   .score(scoreValue)
                                   .build();
        
        RatingEntity saved = ratingRepository.save(newRatingEntity);
        ContentRated event = createEvent(saved);
        contentEventPublisher.publish(event);
        log.debug("content rated: {}", event);
        log.info("평점이 추가되었습니다.");
        return event.getEventId();
    }
    private ContentRated createEvent(RatingEntity saved) {
        return ContentRated.builder()
                           .userId(saved.getUserId())
                           .contentId(saved.getContent().getContentId())
                           .score(saved.getScore())
                           .eventId(UUID.randomUUID())
                           .build();
    }
}
