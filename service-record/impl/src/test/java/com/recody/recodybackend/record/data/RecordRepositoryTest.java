package com.recody.recodybackend.record.data;

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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyRecordApplication.class)
class RecordRepositoryTest {
    
    public static final int RECORD_LENGTH_2 = 10;
    public static final int RECORD_LENGTH = 100;
    public static final EmbeddableCategory commonCategory = new EmbeddableCategory("11", "na");
    public static final EmbeddableCategory commonCategory2 = new EmbeddableCategory("22", "NN");
    public static final long USER_ID = 1L;
    public static final long USER_ID_2 = 2L;
    @Autowired
    RecordRepository recordRepository;
    private final List<RecordEntity> savedRecords = new ArrayList<>();
    private final Map<String, RecordEntity> savedRecordsMap = new HashMap<>();
    
    @Autowired
    RecordContentRepository recordContentRepository;
    
    RecordContentEntity savedContent;
    RecordContentEntity savedContent2;
    
    
    
    @BeforeEach
    void before() {
        // 삭제 순서 유의
        recordRepository.deleteAllInBatch();
        recordContentRepository.deleteAllInBatch();
        RecordContentEntity contentEntity = RecordContentEntity
                                            .builder()
                                            .id("cc1")
                                            .contentId("c1")
                                            .category(commonCategory)
                                            .title("conTitle")
                                            .build();
        RecordContentEntity contentEntity2 = RecordContentEntity
                                                    .builder()
                                                    .id("cc2")
                                                    .contentId("c2")
                                                    .category(commonCategory2)
                                                    .title("conTitle")
                                                    .build();
        savedContent = recordContentRepository.save(contentEntity);
        savedContent2 = recordContentRepository.save(contentEntity2);
        
        for (int i = 0; i < RECORD_LENGTH; i++) {
            RecordEntity saved = recordRepository.save(newRecord(savedContent, USER_ID));
            savedRecords.add(saved);
            savedRecordsMap.put(saved.getRecordId(), saved);
        }
    
        for (int i = 0; i < RECORD_LENGTH_2; i++) {
            RecordEntity saved = recordRepository.save(newRecord(savedContent2, USER_ID_2));
            savedRecords.add(saved);
            savedRecordsMap.put(saved.getRecordId(), saved);
        }
    }
    
    private RecordEntity newRecord(RecordContentEntity content, Long userId) {
        return RecordEntity.builder().content(content).note("testing").userId(userId).completed(true).build();
    }
    
    @Test
    @DisplayName("결과 최근부터 10개 가져오기")
    void top10() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        Optional<List<RecordEntity>> records = recordRepository.findByUserIdOrderByCreatedAtDesc(USER_ID, pageable);
        Optional<List<RecordEntity>> allRecords = recordRepository.findAllByUserId(USER_ID);
        List<RecordEntity> recordEntities = records.orElseThrow();
        List<RecordEntity> allRecordEntities = allRecords.orElseThrow();
    
        // when
        for (RecordEntity recordEntity : recordEntities) {
            System.out.println(recordEntity);
        }
        
        List<RecordEntity> top10 = allRecordEntities
                .stream()
                .sorted(Comparator.comparing(RecordBaseEntity::getCreatedAt).reversed())
                .limit(10)
                .collect(Collectors.toList());
        
        System.out.println("----------------------");
        for (RecordEntity recordEntity : top10) {
            System.out.println(recordEntity);
        }
    
    
        // then
        assertThat(recordEntities).isEqualTo(top10);
    
    }
    
    @Test
    @DisplayName("결과 최근부터 10개 씩 두번째 페이지")
    void top10SecondPage() {
        // given
        PageRequest pageable = PageRequest.of(1, 10);
        Optional<List<RecordEntity>> records = recordRepository.findByUserIdOrderByCreatedAtDesc(USER_ID, pageable);
        Optional<List<RecordEntity>> allRecords = recordRepository.findAllByUserId(USER_ID);
        List<RecordEntity> recordEntities = records.orElseThrow();
        List<RecordEntity> allRecordEntities = allRecords.orElseThrow();
        
        // when
        for (RecordEntity recordEntity : recordEntities) {
            System.out.println(recordEntity);
        }
        
        List<RecordEntity> top10SecondPage = allRecordEntities
                .stream()
                .sorted(Comparator.comparing(RecordBaseEntity::getCreatedAt).reversed())
                .limit(20)
                .sorted(Comparator.comparing(RecordBaseEntity::getCreatedAt))
                .limit(10)
                .collect(Collectors.toList());
        
        System.out.println("----------------------");
        for (RecordEntity recordEntity : top10SecondPage) {
            System.out.println(recordEntity);
        }
        
        
        // then
        assertThat(recordEntities).containsAll(top10SecondPage);
        
    }
    
    @Test
    @DisplayName("결과가 size 만큼 없어도 에러가 나지 않는다. ")
    void top10SecondPageLacksAmount() {
        // given
        // 총 100개만 before 에서 넣었는데, 두번째 페이지를 가져오면 0개이다.
        PageRequest pageable = PageRequest.of(2, 100);
        Optional<List<RecordEntity>> records = recordRepository.findByUserIdOrderByCreatedAtDesc(USER_ID, pageable);
        List<RecordEntity> recordEntities = records.orElseThrow();
        
        // when
        for (RecordEntity recordEntity : recordEntities) {
            System.out.println(recordEntity);
        }
        
        
        // then
        assertThat(recordEntities.size()).isEqualTo(0);
        assertThat(recordEntities).isEmpty();
        
    }
    
    @Test
    @DisplayName("가장 최근에 수정된 감상평을 가져온다.")
    void findFirstBy() {
        // given
        String recordId2 = changeCompletedStatusAt(49);
        String recordId = changeCompletedStatusAt(50);
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        
        // when
        Optional<RecordEntity> optionalRecord = recordRepository.findFirstByUserIdAndCompletedIsFalseOrderByLastModifiedAtDesc(USER_ID);
    
        // then
        assertThat(optionalRecord).isNotEmpty();
        assertThat(optionalRecord.get().getRecordId()).isEqualTo(recordId);
        
    }
    
    
    private String changeCompletedStatusAt(int index) {
        RecordEntity recordEntity = savedRecords.get(index);
        String recordId = recordEntity.getRecordId();
        Optional<RecordEntity> foundRecord = recordRepository.findByRecordId(recordId);
        assertThat(foundRecord).isNotEmpty();
        RecordEntity recordEntity1 = foundRecord.get();
        recordEntity1.setCompleted(false);
        return recordId;
    }
    
    @Test
    @DisplayName("작품 카테고리로 감상평을 필터링하여 가져올 수 있다.")
    void categoryFilterTest() {
        // given
        
        List<RecordEntity> records = recordRepository
                                             .findAllFetchJoinContentOnCategory(commonCategory);
        List<RecordEntity> records2 = recordRepository.findAllFetchJoinContentOnCategory(
                commonCategory2);
        List<RecordEntity> all = recordRepository.findAll();
        System.out.println("all.size() = " + all.size());
        // when
    
        // then
        assertThat(records.size()).isEqualTo(RECORD_LENGTH);
        assertThat(records2.size()).isEqualTo(RECORD_LENGTH_2);
        
        for (RecordEntity record : records) {
            assertThat(record.getContent().getCategory())
                    .isEqualTo(savedRecordsMap.get(record.getRecordId()).getContent().getCategory());
        }
    
        for (RecordEntity record2 : records2) {
            assertThat(record2.getContent().getCategory())
                    .isEqualTo(savedRecordsMap.get(record2.getRecordId()).getContent().getCategory());
        }
        
    }
    
    @Test
    @DisplayName("해당 유저 ID 와 카테고리로 조인하여 가져올 수 있다.")
    void CategoryAndUserIdFetch() {
        // given
        List<RecordEntity> records2 = recordRepository.findAllFetchJoinContentWhereCategoryAndUserId(
                commonCategory2, USER_ID_2);
        List<RecordEntity> all = recordRepository.findAll();
        System.out.println("CategoryAndUserIdFetch: all.size() = " + all.size());
        assertThat(records2.size()).isEqualTo(RECORD_LENGTH_2);
    
    
        // when
        
        // then
        for (RecordEntity record2 : records2) {
            assertThat(record2.getContent().getCategory())
                    .isEqualTo(savedRecordsMap.get(record2.getRecordId()).getContent().getCategory());
            assertThat(record2.getUserId()).isEqualTo(USER_ID_2);
        }
    }
    
}