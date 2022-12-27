package com.recody.recodybackend.catalog.features.wish;

import com.recody.recodybackend.common.contents.ContentId;
import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import com.recody.recodybackend.catalog.features.wish.add.AddToWishlist;
import com.recody.recodybackend.catalog.features.wish.add.AddToWishlistHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class DefaultAddToWishlistHandlerTest {
    
    @Mock
    AddToWishlistHandler addToWishlistHandler;
    
    @BeforeEach
    void before() {
    
    }
    
    
    @Test
    @DisplayName("test01")
    void test01() {
        // given
        AddToWishlist command = AddToWishlist.builder().contentId( ContentId.of( "4232" ) ).userId( 32423L ).build();
        UUID catalogId = UUID.randomUUID();

        
        // when
        when(addToWishlistHandler.handle(command))
                .thenReturn(catalogId);
        
        // then
        assertThat(addToWishlistHandler.handle(command)).isEqualTo(catalogId);
    }
}