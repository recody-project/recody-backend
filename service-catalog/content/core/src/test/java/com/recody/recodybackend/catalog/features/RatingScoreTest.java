package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.rating.RatingScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RatingScoreTest {
    
    @Test
    @DisplayName( "Rating Score 의 값은 null 일 수 없다" )
    void check0orNull() {
        // given
        Integer receivedValue = null;
        // when
    
        // then
        assertThatThrownBy( () -> RatingScore.of( receivedValue ) );
    }
    
}