package com.recody.recodybackend.record.features.addrecord;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.record.RecodyRecordApplication;
import com.recody.recodybackend.record.data.category.EmbeddableCategory;
import com.recody.recodybackend.record.data.content.RecordContentEntity;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import com.recody.recodybackend.record.data.rating.RecordRatingEntity;
import com.recody.recodybackend.record.data.rating.RecordRatingRepository;
import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordRepository;
import com.recody.recodybackend.record.exceptions.UserNotRatedOnContentException;
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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyRecordApplication.class)
@ExtendWith(MockitoExtension.class)
class DefaultAddRecordHandlerTest {
    
    public static final String OTHER_CONTENT_ID = "otherContentId";
    public static final String RECORD_ID = "recordId";
    public static final String SAMPLE_NOTE = "sampleNote";
    public static final String CONTENT_ID = "contentId";
    public static final long USER_ID = 123L;
    public static final String SAMPLE_TITLE = "sampleTitle";
    
    
    @Autowired
    DefaultAddRecordHandler addRecordHandler;
    
    @Autowired
    RecordRepository recordRepository;
    
    @Autowired
    RecordContentRepository contentRepository;
    
    @Autowired
    RecordRatingRepository recordRatingRepository;
    
    String recordId;
    
    RecordContentEntity content;
    
    @BeforeEach
    void before() {
        content = RecordContentEntity
                            .builder()
                            .id("catalogId")
                            .contentId(CONTENT_ID)
                            .title("contentTitle")
                            .category(new EmbeddableCategory(BasicCategory.Movie.getId(), BasicCategory.Movie.name()))
                            .build();
        contentRepository.save(content);
    }
    
    @Test
    @DisplayName("감상평을 추가할 수 있다. 감상일은 추가하지 않아도 예외가 일어나지 않는다.")
    void addRecordAppreciationDate() {
        // given
        RecordRatingEntity ratingEntity = RecordRatingEntity.builder().score(1).userId(USER_ID).content(content).build();
        recordRatingRepository.save(ratingEntity);
        
        // when
        AddRecord command = AddRecord
                .builder()
                .title(SAMPLE_TITLE)
                .userId(USER_ID)
                .contentId(CONTENT_ID)
                .note(SAMPLE_NOTE)
                .build();
        
        
        
        // then
        assertThatNoException().isThrownBy(() -> recordId = addRecordHandler.handle(command));
        
    }
    
    @Test
    @DisplayName("감상일을 추가할 수 있다.")
    void addRecordAppreciationDate2() {
        // given
        RecordRatingEntity ratingEntity = RecordRatingEntity.builder().score(1).userId(USER_ID).content(content).build();
        recordRatingRepository.save(ratingEntity);
        
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
        Optional<RecordEntity> optionalRecord = recordRepository.findByRecordId(recordId);
        assertThat(optionalRecord).isNotEmpty();
        assertThat(optionalRecord.get().getAppreciationDate()).isEqualTo(date);
    
    
    }
    
    @Test
    @DisplayName("감상평을 저장할 때 없는 작품이면 예외를 일으킨다.")
    void addRecordContentCheck() {
        // given
        RecordRatingEntity ratingEntity = RecordRatingEntity.builder().score(1).userId(USER_ID).content(content).build();
        recordRatingRepository.save(ratingEntity);
        // when
        assertThatThrownBy(() -> addRecordHandler.handle(AddRecord.builder()
                                                                  .userId(USER_ID)
                                                                  .contentId(OTHER_CONTENT_ID)
                                                                  .note(SAMPLE_NOTE).build()))
                .isInstanceOf(ContentNotFoundException.class);
        
    
        // then
    }
    
    @Test
    @DisplayName("감상평을 저장할때 평점이 없으면 예외를 던진다.")
    void ratingCheck() {
        // given
        
        // when
        
        // then
        assertThatThrownBy(() -> addRecordHandler.handle(AddRecord.builder()
                                                                  .userId(USER_ID)
                                                                  .contentId(CONTENT_ID)
                                                                  .note(SAMPLE_NOTE).build()))
                .isInstanceOf(UserNotRatedOnContentException.class);
    }
    
    @AfterEach
    void after(){
        recordRatingRepository.deleteAllInBatch();
        recordRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
    }
}