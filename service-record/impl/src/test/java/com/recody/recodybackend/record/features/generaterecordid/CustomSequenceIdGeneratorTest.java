package com.recody.recodybackend.record.features.generaterecordid;

import com.recody.recodybackend.record.RecodyRecordApplication;
import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyRecordApplication.class)
class CustomSequenceIdGeneratorTest {
    
    @Autowired
    RecordRepository repository;
    
    @Test
    @DisplayName("저장 시 id 체크")
    void test02() {
        // given
        long userId = 1L;
        ArrayList<RecordEntity> recordEntities = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            RecordEntity recordEntity = RecordEntity.builder().contentId(UUID.randomUUID().toString()).note(UUID.randomUUID().toString()).userId(userId++).build();
            recordEntities.add(recordEntity);
        }
        
        
    
        // when
        // then
    
        for (RecordEntity recordEntity : recordEntities) {
            RecordEntity saved = repository.save(recordEntity);
            System.out.println("saved = " + saved);
            String recordId = saved.getRecordId();
            System.out.println("recordId = " + recordId);
            assertThat(recordId).startsWith("r");
        }
    }
    
}