package com.recody.recodybackend.catalog.data;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class LookupIdTest {
    
    @Autowired TestEntityRepository testEntityRepository;
    @Autowired
    LookupReferenceRepository lookupReferenceRepository;
    
    
    @Test
    @DisplayName("embeddedId 를 사용하는 엔티티가 정상적으로 저장되는가?")
    void test02() {
        // given
        LookupId lookupId = new LookupId("user1", "content1");
        // when
        TestEntity testEntity = new TestEntity(lookupId, "testName1");
    
        TestEntity saved = testEntityRepository.save(testEntity);
    
        // then
        assertThat(saved.getLookupId()).isEqualTo(lookupId);
    }
    
    @Test
    @DisplayName("lookup 이벤트는 제대로 저장되는가?")
    void test03() {
        // given
        LookupId lookupId = new LookupId("user1", "content1");
    
        // when
        LookupReference event = new LookupReference(lookupId);
    
        LookupReference saved = lookupReferenceRepository.save(event);
        
        // then
        assertThat(saved.getReferenceId()).isEqualTo(event.getReferenceId());
    }
}