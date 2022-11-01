package com.recody.recodybackend.catalog.features.setcustomcategory;

import com.recody.recodybackend.catalog.ContentId;
import com.recody.recodybackend.catalog.CustomCategoryId;
import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.PersonalizedCategoryEntity;
import com.recody.recodybackend.catalog.data.category.PersonalizedCategoryRepository;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith( MockitoExtension.class )
@SpringBootTest
@ActiveProfiles( "test" )
@ContextConfiguration( classes = RecodyCatalogApplication.class )
class DefaultSetCustomCategoryOnContentHandlerTest {
    
    public static final String CATEGORY_ID = "cat-6";
    public static final String CONTENT_ID = "mov-1";
    public static final long USER_ID = 123L;
    @Autowired
    SetCustomCategoryOnContentHandler setCustomCategoryOnContentHandler;
    
    @Autowired
    PersonalizedCategoryRepository personalizedCategoryRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    CatalogContentRepository contentRepository;
    
    CatalogContentEntity savedContent;
    
    @BeforeEach
    void before() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                                              .id( CATEGORY_ID )
                                              .userId( USER_ID )
                                              .name( "random" )
                                              .build();
        CategoryEntity savedCategory = categoryRepository.save( categoryEntity );
        CatalogContentEntity contentEntity = CatalogContentEntity.builder()
                                                         .contentId( CONTENT_ID )
                                                                 .title( "sampleTitle" )
                                                         .category( savedCategory )
                                                         .build();
        savedContent = contentRepository.save( contentEntity );
    }
    @Test
    @DisplayName( "커스텀 카테고리를 작품에 등록하면 개인화된 커스텀 카테고리 정보가 저장된다." )
    void setCustomCategory() {
        // given
        SetCustomCategoryOnContent command = SetCustomCategoryOnContent.builder()
                                                                     .contentId( ContentId.of( CONTENT_ID ) )
                                                                     .userId( USER_ID )
                                                                     .categoryId( CustomCategoryId.of( CATEGORY_ID ) )
                                                                     .build();
    
        // when
        CategoryPersonalized handle = setCustomCategoryOnContentHandler.handle( command );
    
        // then
        Optional<PersonalizedCategoryEntity> optionalCategoryPersonalization
                = personalizedCategoryRepository.findByUserIdAndContent( USER_ID, savedContent );
    
        assertThat( optionalCategoryPersonalization ).isNotEmpty();
        assertThat( handle.getCategoryId() ).isEqualTo( CATEGORY_ID );
        assertThat( handle.getUserId() ).isEqualTo( USER_ID );
        assertThat( handle.getContentId() ).isEqualTo(  CONTENT_ID );
    }
    
}