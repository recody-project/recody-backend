package com.recody.recodybackend.record.features.eventhandlers;

import com.recody.recodybackend.common.events.ContentCreated;
import com.recody.recodybackend.common.events.ContentRated;
import com.recody.recodybackend.common.events.RecodyTopics;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentMapper;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import com.recody.recodybackend.record.data.rating.RecordRatingEntity;
import com.recody.recodybackend.record.data.rating.RecordRatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = RecodyTopics.CONTENT, groupId = "record")
class KafkaContentEventHandler implements ContentEventHandler{
    
    private final RecordContentRepository contentRepository;
    private final RecordContentMapper contentMapper;
    
    private final RecordRatingRepository ratingRepository;
    
    @Override
    @KafkaHandler
    public void on(@Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) String key,
                   @Payload ContentCreated event) {
        RecordContentEntity entity = contentMapper.map(event);
        RecordContentEntity savedEntity = contentRepository.save(entity);
        log.info("Content Saved On Record Service:  key:{}, payload:{}", key, savedEntity);
    }
    
    @Override
    @KafkaHandler
    @Transactional
    public void on(@Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) String key,
                   @Payload ContentRated event) {
        Long userId = event.getUserId();
        Integer score = event.getScore();
        RecordContentEntity recordContentEntity =
                contentRepository.findByContentId(event.getContentId()).orElseThrow(ContentNotFoundException::new);
        Optional<RecordRatingEntity> optionalRating =
                ratingRepository.findByUserIdAndContent(userId, recordContentEntity);
        if (optionalRating.isPresent()){
            RecordRatingEntity recordRatingEntity = optionalRating.get();
            recordRatingEntity.setScore(score);
            return;
        }
        RecordRatingEntity ratingEntity = RecordRatingEntity.builder()
                                                            .userId(userId)
                                                            .content(recordContentEntity)
                                                            .score(score)
                                                            .build();
        ratingRepository.save(ratingEntity);
        log.info("Content Rated On Record Service: key:{}, payload:{}", key, event);
    }
}
