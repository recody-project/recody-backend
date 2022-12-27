package com.recody.recodybackend.catalog.features.wish.get;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentTitleEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.catalog.data.wish.WishEntity;
import com.recody.recodybackend.catalog.data.wish.WishRepository;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Content;
import com.recody.recodybackend.users.Role;
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

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class DefaultGetMyWishlistHandlerTest {
    
    public CategoryEntity COMMON_CATEGORY;
    public static final long USER_ID = 10L;
    @Autowired GetMyWishlistHandler getMyWishlistHandler;
    @Autowired
    WishRepository wishRepository;
    @Autowired
    CatalogContentRepository contentRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    CatalogUserRepository userRepository;
    
    CatalogUserEntity savedUser;
    String[] contentIds = {"mov-1", "mov-2", "mov-3"};
    
    @BeforeEach
    void before() {
        wishRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        CatalogUserEntity userEntity = CatalogUserEntity.builder()
                                                        .id( USER_ID )
                                                        .email( "EMAIL" ).role( Role.ROLE_MEMBER )
                                                        .build();
        savedUser = userRepository.save( userEntity );
        String id = BasicCategory.Movie.getId();
        System.out.println(id);
        CategoryEntity categoryEntity = new CategoryEntity(id, BasicCategory.Movie.getName());
        COMMON_CATEGORY = categoryRepository.save(categoryEntity);
        for (int i = 0; i < contentIds.length; i++) {
            CatalogContentEntity con1 = CatalogContentEntity.builder()
                                                            .contentId(contentIds[i])
                                                            .category(COMMON_CATEGORY).build();
            CatalogContentTitleEntity titleEntity = CatalogContentTitleEntity.builder().englishTitle( "TITLE OF" + contentIds[i] ).build();
            con1.setTitle( titleEntity );
            CatalogContentEntity savedEntity = contentRepository.save(con1);
            WishEntity wishEntity = WishEntity.builder().catalogContent( savedEntity ).user( savedUser ).build();
            WishEntity saved = wishRepository.save(wishEntity);
            System.out.println("saved = " + saved);
        }
    }
    
    @Test
    @DisplayName("content 들이 저장되어 있고 위시 표시를 했다면, wishlist 를 가져왔을 때 같은 contentId 들을 가지는 작품들을 가져올 수 있다.")
    void test01() {
        // given
        GetMyWishlist command = GetMyWishlist.builder().userId(USER_ID).locale( Locale.KOREAN ).build();
    
    
    
        // when
        List<Content<?>> handle = getMyWishlistHandler.handle(command);

        
        // then
        for (int i = 0, handleSize = handle.size(); i < handleSize; i++) {
            Content<?> catalogContent = handle.get(i);
            assertThat(catalogContent.getContentId()).isEqualTo(contentIds[i]);
        }
    }
    
    @AfterEach
    void after() {
        wishRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }
}