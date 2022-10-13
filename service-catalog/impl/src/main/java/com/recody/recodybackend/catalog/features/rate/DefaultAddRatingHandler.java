package com.recody.recodybackend.catalog.features.rate;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.rating.RatingEntity;
import com.recody.recodybackend.catalog.data.rating.RatingRepository;
import com.recody.recodybackend.catalog.exceptions.InvalidRatingScoreException;
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
    
    private final RatingRepository ratingRepository;
    
    private final ContentEventPublisher contentEventPublisher;
    private final CatalogContentRepository contentRepository;
    
    @Override
    @Transactional
    public UUID handle(AddRating command) {
        log.debug("handing command: {}", command);
        Long userId = command.getUserId();
        String contentId = command.getContentId();
        int score = command.getScore();
        if (score <= 0 || score > 10){
            throw new InvalidRatingScoreException();
        }
        Optional<CatalogContentEntity> optionalContent = contentRepository.findByContentId(contentId);
        CatalogContentEntity contentEntity = optionalContent.orElseThrow(ContentNotFoundException::new);
        RatingEntity ratingEntity = RatingEntity.builder().userId(userId).content(contentEntity).score(score).build();
        RatingEntity saved = ratingRepository.save(ratingEntity);
        ContentRated event = ContentRated.builder()
                                         .userId(saved.getUserId())
                                         .contentId(saved.getContent().getContentId())
                                         .score(saved.getScore())
                                         .eventId(UUID.randomUUID())
                                         .build();
        contentEventPublisher.publish(event);
        return event.getEventId();
    }
}
