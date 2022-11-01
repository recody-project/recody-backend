package com.recody.recodybackend.catalog.features.category.add;

import com.recody.recodybackend.catalog.CategoryIconUrl;
import com.recody.recodybackend.catalog.CategoryName;
import com.recody.recodybackend.catalog.CustomCategory;
import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import com.recody.recodybackend.catalog.exceptions.CustomCategoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith( MockitoExtension.class )
@SpringBootTest
@ActiveProfiles( "test" )
@ContextConfiguration( classes = RecodyCatalogApplication.class )
class DefaultAddCategoryHandlerTest {
    
    public static final CategoryIconUrl ICON_URL = CategoryIconUrl.of( "ICON_URL" );
    public static final long USER_ID = 123L;
    @Autowired
    AddCategoryHandler addCategoryHandler;
    
    @Test
    @DisplayName( "한 유저당 카테고리는 10개까지 가능하다, 기본 카테고리 5개, 커스텀 카테고리 5개" )
    void categoryUserCheck() {
        // given
        // 4개를 넣는다.
        for (int i = 0; i < 4; i++) {
            AddCategory command = newCommandWithSameUser(UUID.randomUUID().toString().substring(0, 7));
            addCategoryHandler.handle(command);
        }
        
        // when
        
        // then
        
        assertThatThrownBy(() -> addCategoryHandler.handle(newCommandWithSameUser(UUID.randomUUID().toString())))
                .isInstanceOf(CustomCategoryException.class);
    
    }
    
    private static AddCategory newCommandWithSameUser(String name) {
        return AddCategory.builder().name( CategoryName.of( name ) ).userId( USER_ID ).iconUrl( ICON_URL ).build();
    }
    
    @Test
    @DisplayName( "한 유저는 같은 이름의 카테고리를 추가할 수 없다." )
    void userCannotAddSameCategory() {
        // given
        AddCategory command1 = newCommandWithSameUser("1");
        AddCategory command2 = newCommandWithSameUser("1");
        // when
        CustomCategory handled = addCategoryHandler.handle( command1 );
        
        // then
        assertThatThrownBy(() -> addCategoryHandler.handle(command2))
                .isInstanceOf(CustomCategoryException.class);
    }
    
    @Test
    @DisplayName( "이름은 null 이거나 빈 값일 수 없다." )
    void nameCannotBeBlank() {
        
        assertThatThrownBy(
                () -> {
                    AddCategory nullCommand = newCommandWithSameUser(null);
                    AddCategory blankCommand = newCommandWithSameUser("");
                }
                          );
    }
    
}