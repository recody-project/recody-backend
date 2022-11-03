package com.recody.recodybackend.catalog.data.rating;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import com.recody.recodybackend.catalog.RatingScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class RatingMapperTest {
    
    @Autowired
    RatingMapper ratingMapper;
    
    @Test
    @DisplayName( "rating 매핑이 잘 되는가" )
    void test01() {
        // given
        RatingEntity entity = RatingEntity.builder().score( 1 ).build();
        
        // when
        RatingScore score = ratingMapper.map( entity );
        // then
    
        assertThat( score.getValue() ).isEqualTo( entity.getScore() );
        
    }
    
}