package com.recody.recodybackend.record.features.eventhandlers;

import com.recody.recodybackend.common.events.ContentCreated;
import com.recody.recodybackend.common.events.MMM;
import com.recody.recodybackend.record.data.content.RecordContentMapper;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class KafkaContentEventHandler implements ContentEventHandler{
    
    public static final String TEST_TOPIC = "testTopic";
    
    public static final String TEST_TOPIC2 = "testTopic2";
    public static final String CONTENT_TOPIC = "content";
    private final RecordContentRepository contentRepository;
    private final RecordContentMapper contentMapper;
    
    @Override
    @KafkaListener(topics = {CONTENT_TOPIC}, groupId = "record")
    public void on(ContentCreated event) {
        RecordContentEntity entity = contentMapper.map(event);
        RecordContentEntity savedEntity = contentRepository.save(entity);
        log.info("Content Saved On Record Service: {}", savedEntity);
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
