package com.recody.recodybackend.catalog.features.record.completerecord;

import com.recody.recodybackend.catalog.CatalogRecordConfiguration;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.users.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = CatalogRecordConfiguration.class)
class DefaultCompleteRecordHandlerTest {
    
    private static final String CONTENT_ID = "con-1";
    public static final long USER_ID = 1L;
    public static final String OLD_NOTE = "a";
    @Autowired CompleteRecordHandler completeRecordHandler;
    @Autowired
    RecordRepository recordRepository;
    
    @Autowired
    CatalogContentRepository contentRepository;
    CatalogContentEntity savedContent;
    
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CatalogUserRepository userRepository;
    
    CatalogUserEntity savedUser;
    
    @BeforeEach
    void before() {
        CatalogUserEntity userEntity = CatalogUserEntity.builder()
                                                        .id( USER_ID )
                                                        .email( "EMAIL" ).role( Role.ROLE_MEMBER )
                                                        .build();
        savedUser = userRepository.save( userEntity );
        CategoryEntity categoryEntity = new CategoryEntity( BasicCategory.Movie.getId(), BasicCategory.Movie.getName() );
        categoryRepository.save( categoryEntity );
        CatalogContentEntity content = CatalogContentEntity
                                              .builder()
                                              .id("catalogId")
                                              .contentId(CONTENT_ID)
                                              .category( categoryEntity )
                                              .build();
        savedContent = contentRepository.save(content);
        
    }
    
    
    @Test
    @DisplayName("완료 처리 여부 확인하기")
    void test01() {
        // given
        RecordEntity first = RecordEntity.builder().user( savedUser ).content( savedContent ).note( OLD_NOTE ).build();
        RecordEntity savedRecord = recordRepository.save( first );
        String recordId = savedRecord.getRecordId();
    
        // when
        boolean isCompleted = completeRecordHandler.handle(
                CompleteRecord.builder().recordId(recordId).userId( USER_ID ).build());
        
    
        // then
        assertThat(isCompleted).isTrue();
        Optional<RecordEntity> optionalRecord = recordRepository.findByRecordId( recordId );
        assertThat(optionalRecord).isNotEmpty();
    }
    @AfterEach
    void after() {
        recordRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }
}