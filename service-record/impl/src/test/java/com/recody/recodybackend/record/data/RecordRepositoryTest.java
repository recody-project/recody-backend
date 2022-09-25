package com.recody.recodybackend.record.data;

import com.recody.recodybackend.record.RecodyRecordApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyRecordApplication.class)
class RecordRepositoryTest {
    
    public static final long USER_ID = 1L;
    @Autowired RecordRepository recordRepository;
    private final List<RecordEntity> savedRecords = new ArrayList<>();
    
    @BeforeEach
    void before() {
        for (int i = 0; i < 100; i++) {
            RecordEntity saved = recordRepository.save(newRecord());
            savedRecords.add(saved);
        }
    }
    
    private RecordEntity newRecord() {
        return RecordEntity.builder().contentId("asdf").note("testing").userId(USER_ID).completed(true).build();
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
    
}