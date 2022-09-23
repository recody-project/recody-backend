package com.recody.recodybackend.record.features.completerecord;

import com.recody.recodybackend.record.RecodyRecordApplication;
import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyRecordApplication.class)
class DefaultCompleteRecordHandlerTest {
    
    public static final String NEW_NOTE = "b";
    public static final String OLD_NOTE = "a";
    @Autowired CompleteRecordHandler completeRecordHandler;
    @Autowired
    RecordRepository repository;
    
    
    @Test
    @DisplayName("완료 처리 여부 확인하기")
    void test01() {
        // given
        RecordEntity first = RecordEntity.builder().userId(1L).contentId("3").note(OLD_NOTE).build();
        RecordEntity savedRecord = repository.save(first);
        String recordId = savedRecord.getRecordId();
    
        // when
        boolean isCompleted = completeRecordHandler.handle(
                CompleteRecord.builder().recordId(recordId).note(NEW_NOTE).build());
        
    
        // then
        assertThat(isCompleted).isTrue();
        Optional<RecordEntity> optionalRecord = repository.findByRecordId(recordId);
        assertThat(optionalRecord).isNotEmpty();
    
        RecordEntity recordEntity = optionalRecord.get();
        assertThat(recordEntity.getNote()).isEqualTo(NEW_NOTE);
    }
}