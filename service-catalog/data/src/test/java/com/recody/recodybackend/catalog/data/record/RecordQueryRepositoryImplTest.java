package com.recody.recodybackend.catalog.data.record;

import com.recody.recodybackend.catalog.data.CatalogDataConfiguration;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = CatalogDataConfiguration.class)
class RecordQueryRepositoryImplTest {
    
    public static final String CONTENT_ID_PREFIX = "mov-";
    public static final long USER_ID = 123L;
    
    @Autowired
    RecordRepository repository;
    
    @Autowired
    CatalogContentRepository contentRepository;
    
    @BeforeEach
    void before() {
    
    }
    @Test
    @DisplayName( "특정 감상 날짜 이후의 감상평의 개수만 센다." )
    void test01() {
        // given
        CategoryEntity categoryEntity = new CategoryEntity( "cat-1", "name" );
        int total = 100;
        int beforeCount = 10;
        int afterCount = total - beforeCount;
    
        LocalDate dateBefore = LocalDate.of( 2021, 2, 1 );
        LocalDate dateAfter = LocalDate.of( 2022, 2, 1 );
        LocalDate dateTarget = LocalDate.of( 2022, 1, 1 );
        
        ArrayList<CatalogContentEntity> contentList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            CatalogContentEntity catalogContentEntity = new CatalogContentEntity( categoryEntity, CONTENT_ID_PREFIX + i );
            CatalogContentEntity savedContent = contentRepository.save( catalogContentEntity );
            contentList.add( savedContent );
        }
        // 기준 날짜 이전에 감상한 감상평들.
        for (int i = 0; i < beforeCount; i++) {
            RecordEntity recordEntity = RecordEntity.builder()
                                                    .userId( USER_ID )
                                                    .title( "title" )
                                                    .content( contentList.get( i ) )
                                                    .appreciationDate( dateBefore )
                                                    .build();
            repository.save( recordEntity );
        }
        // 기준날짜 이후에 감상한 감상평들.
        for (int i = beforeCount; i < total; i++) {
            RecordEntity recordEntity = RecordEntity.builder()
                                                    .userId( USER_ID )
                                                    .title( "title" )
                                                    .content( contentList.get( i ) )
                                                    .appreciationDate( dateAfter )
                                                    .build();
            repository.save( recordEntity );
        }

    
        // when
    
        Integer count = repository.countByUserIdAndAppreciationDateAfter( USER_ID, dateTarget );
        // then
        
        assertThat( count ).isEqualTo( afterCount );
    }
    
}