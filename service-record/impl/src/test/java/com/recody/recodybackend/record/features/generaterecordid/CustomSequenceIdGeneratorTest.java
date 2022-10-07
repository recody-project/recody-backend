package com.recody.recodybackend.record.features.generaterecordid;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.record.RecodyRecordApplication;
import com.recody.recodybackend.record.data.category.EmbeddableCategory;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
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
    
    private static final String CONTENT_ID = "con-1";
    
    @Autowired
    RecordRepository repository;
    
    @Autowired
    RecordContentRepository contentRepository;
    RecordContentEntity savedContent;
    
    @BeforeEach
    void before() {
        RecordContentEntity content = RecordContentEntity
                                              .builder()
                                              .id("catalogId")
                                              .contentId(CONTENT_ID)
                                              .title("contentTitle")
                                              .category(new EmbeddableCategory(BasicCategory.Movie.getId(), BasicCategory.Movie.name()))
                                              .build();
        savedContent = contentRepository.save(content);
    }
    
    @Test
    @DisplayName("저장 시 id 체크")
    void test02() {
        // given
        long userId = 1L;
        ArrayList<RecordEntity> recordEntities = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            RecordEntity recordEntity = RecordEntity.builder().content(savedContent).note(UUID.randomUUID().toString()).userId(userId++).build();
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