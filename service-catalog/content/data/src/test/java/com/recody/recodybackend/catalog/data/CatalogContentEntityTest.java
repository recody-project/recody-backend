//package com.recody.recodybackend.catalog.data;
//
//import com.recody.recodybackend.catalog.RecodyCatalogApplication;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@ContextConfiguration(classes = RecodyCatalogApplication.class)
//class CatalogContentEntityTest {
//
//    public static final String CONTENT_ID = "contentId";
//    public static final String CATEGORY_1 = "Category1";
//    public static final String SAMPLE_URL = "sampleUrl";
//    public static final String SAMPLE_TITLE = "sampleTitle";
//    @Autowired CatalogContentRepository contentRepository;
//    @Autowired CategoryRepository categoryRepository;
//
//    @Test
//    @DisplayName("Content 가 저장될 때 Category 가 같이 저장되는가?")
//    void category() {
//        // given
//        CategoryEntity categoryEntity = CategoryEntity.builder().name(CATEGORY_1).build();
//        // when
//        CatalogContentEntity contentEntity = CatalogContentEntity.builder()
//                                                                 .title(SAMPLE_TITLE)
//                                                                 .contentId(CONTENT_ID)
//                                                                 .imageUrl(SAMPLE_URL)
//                                                                 .category(categoryEntity)
//                                                                 .build();
//        CatalogContentEntity savedContent = contentRepository.save(contentEntity);
//
//
//        // then
//
//        assertThat(savedContent.getCategory().getName()).isEqualTo(categoryEntity.getName());
//        String categoryId = savedContent.getCategory().getId();
//        assertThat(categoryId).isNotNull();
//        System.out.println("categoryId = " + categoryId);
//    }
//
//    @Test
//    @DisplayName("카테고리가 저장되어 있을 때," +
//                 "저장된 카테고리로 작품을 저장하면" +
//                 "정상적으로 저장된다.")
//    void category2() {
//        CategoryEntity categoryEntity = CategoryEntity.builder().name(CATEGORY_1).build();
//
//        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
//
//        // when
//        CatalogContentEntity contentEntity = CatalogContentEntity.builder()
//                                                                 .title(SAMPLE_TITLE)
//                                                                 .contentId(CONTENT_ID)
//                                                                 .imageUrl(SAMPLE_URL)
//                                                                 .category(categoryEntity)
//                                                                 .build();
//        CatalogContentEntity savedContent;
//        assertThatNoException().isThrownBy(() -> {
//            contentRepository.save(contentEntity);
//        });
//
//        savedContent = contentRepository.save(contentEntity);
//
//
//        // then
//
//        assertThat(savedContent.getCategory().getName()).isEqualTo(categoryEntity.getName());
//        String categoryId = savedContent.getCategory().getId();
//        assertThat(categoryId).isNotNull();
//        System.out.println("categoryId = " + categoryId);
//    }
//
//
//}