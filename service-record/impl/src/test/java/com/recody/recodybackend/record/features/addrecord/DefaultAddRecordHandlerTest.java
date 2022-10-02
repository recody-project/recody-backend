package com.recody.recodybackend.record.features.addrecord;

import com.recody.recodybackend.record.RecodyRecordApplication;
import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyRecordApplication.class)
@ExtendWith(MockitoExtension.class)
class DefaultAddRecordHandlerTest {
    
    public static final String SAMPLE_NOTE = "sampleNote";
    public static final String CONTENT_ID = "contentId";
    public static final long USER_ID = 123L;
    public static final String SAMPLE_TITLE = "sampleTitle";
    @Autowired
    AddRecordHandler addRecordHandler;
    @Autowired
    RecordRepository recordRepository;
    
    String recordId;
    
    @BeforeEach
    void before() {
    }
    
    @Test
    @DisplayName("감상평을 추가할 수 있다. 감상일은 추가하지 않아도 예외가 일어나지 않는다.")
    void addRecordAppreciationDate() {
        // given
        
        AddRecord command = AddRecord
                .builder()
                .title(SAMPLE_TITLE)
                .userId(USER_ID)
                .contentId(CONTENT_ID)
                .note(SAMPLE_NOTE)
                .build();
        assertThatNoException().isThrownBy(() -> recordId = addRecordHandler.handle(command));
        
        
        // then
    }
    
    @Test
    @DisplayName("감상일을 추가할 수 있다.")
    void addRecordAppreciationDate2() {
        // given
        LocalDate date = LocalDate.of(2022, 4, 2);
        AddRecord command = AddRecord
                .builder()
                .title(SAMPLE_TITLE)
                .userId(USER_ID)
                .contentId(CONTENT_ID)
                .note(SAMPLE_NOTE)
                .appreciationDate(date)
                .build();
        // when
        recordId = addRecordHandler.handle(command);
        
        // then
        Optional<RecordEntity> op = recordRepository.findByRecordId(recordId);
    
        assertThat(op).isNotEmpty();
        RecordEntity recordEntity = op.get();
        LocalDate appreciationDate = recordEntity.getAppreciationDate();
        assertThat(date).isEqualTo(appreciationDate);
        
    }
    
    @AfterEach
    void after(){
        recordRepository.deleteAll();
    }
}