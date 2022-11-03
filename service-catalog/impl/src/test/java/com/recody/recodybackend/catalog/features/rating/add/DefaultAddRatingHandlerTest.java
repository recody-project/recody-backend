package com.recody.recodybackend.catalog.features.rating.add;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import com.recody.recodybackend.catalog.exceptions.InvalidRatingScoreException;
import com.recody.recodybackend.catalog.RatingScore;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class DefaultAddRatingHandlerTest {
    @Autowired
    DefaultAddRatingHandler addRatingHandler ;
    
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    @DisplayName("점수는 1에서 10점까지 줄 수 있다.")
    void scoreTest(Integer score) throws ContentNotFoundException{
        // given
        
        AddRating command = AddRating.builder().score( RatingScore.of( score ) ).build();
        // when
    
        // then
        
        assertThatThrownBy(() -> addRatingHandler.handle(command));
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-100, -33333, 0, 11, 999})
    @DisplayName("0, 음수나 10 초과 값은 예외를 발생시킨다.")
    void invalidValueTest(Integer score) throws ContentNotFoundException {
        // given
        
        
        // when
        assertThatThrownBy(() -> RatingScore.of(score))
                .isInstanceOf(InvalidRatingScoreException.class);
        
        // then
    }
    
}