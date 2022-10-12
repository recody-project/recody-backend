package com.recody.recodybackend.record.features.eventhandlers;

import com.recody.recodybackend.common.events.ContentCreated;
import com.recody.recodybackend.common.events.ContentRated;
import com.recody.recodybackend.common.events.MMM;
import com.recody.recodybackend.common.events.RecodyTopics;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentMapper;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = RecodyTopics.CONTENT, groupId = "record")
class KafkaContentEventHandler implements ContentEventHandler{
    
    public static final String TEST_TOPIC = "testTopic";
    
    public static final String TEST_TOPIC2 = "testTopic2";
    private final RecordContentRepository contentRepository;
    private final RecordContentMapper contentMapper;
    
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
    public void on(@Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) String key,
                   @Payload ContentRated event) {
        log.info("Content Rated On Record Service: key:{}, payload:{}", key, event);
    }
    
    @KafkaListener(topics = {TEST_TOPIC}, groupId = "testGroup")
    public void on(String msg){
        log.info("TEST_TOPIC: {}, received: {}", TEST_TOPIC, msg);
    }
    
    @KafkaListener(topics = {TEST_TOPIC2}, groupId = "testGroup")
    public void on2(MMM mmm){
        log.info("TEST_TOPIC: {}, received: {}", TEST_TOPIC, mmm);
    }
}
