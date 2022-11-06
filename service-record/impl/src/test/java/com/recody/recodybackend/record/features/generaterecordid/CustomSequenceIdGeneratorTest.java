package com.recody.recodybackend.record.features.generaterecordid;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.record.RecodyRecordApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyRecordApplication.class)
class CustomSequenceIdGeneratorTest {
    
    private static final String CONTENT_ID = "con-1";
    
    @Autowired
    RecordRepository repository;
    
    @Autowired
    CatalogContentRepository contentRepository;
    CatalogContentEntity savedContent;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    CategoryEntity savedCategory;
    
    @BeforeEach
    void before() {
        CategoryEntity categoryEntity = new CategoryEntity( BasicCategory.Movie.getId(), BasicCategory.Movie.getName() );
        savedCategory = categoryRepository.save( categoryEntity );
        CatalogContentEntity content = CatalogContentEntity
                                              .builder()
                                              .id("catalogId")
                                              .contentId(CONTENT_ID)
                                              .category( savedCategory )
                                              .build();
        savedContent = contentRepository.save(content);
    }
    
    @Test
    @DisplayName("저장 시 id 체크")
    @Transactional
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