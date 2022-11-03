package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.catalog.ContentId;
import com.recody.recodybackend.common.contents.BasicCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentIdTest {
    
    @Test
    @DisplayName( "contentId 에서 기본 카테고리를 알아낼 수 있다." )
    void parseId() {
        // given
        ContentId contentId = ContentId.of( "mov-1" );
        // when
        
        // then
        BasicCategory basicCategory = contentId.parseCategory();
        assertThat( basicCategory ).isEqualTo( BasicCategory.Movie );
    }
    
}