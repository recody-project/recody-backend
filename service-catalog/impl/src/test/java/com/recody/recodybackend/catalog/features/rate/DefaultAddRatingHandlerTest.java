package com.recody.recodybackend.catalog.features.rate;

import com.recody.recodybackend.catalog.RecodyCatalogApplication;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.rating.RatingEntity;
import com.recody.recodybackend.catalog.data.rating.RatingRepository;
import com.recody.recodybackend.catalog.exceptions.InvalidRatingScoreException;
import com.recody.recodybackend.catalog.features.projection.ContentEventPublisher;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyCatalogApplication.class)
class DefaultAddRatingHandlerTest {
    
    DefaultAddRatingHandler addRatingHandler;
    
    @MockBean
    CatalogContentRepository contentRepository;
    
    @MockBean
    RatingRepository ratingRepository;
    
    @MockBean
    ContentEventPublisher contentEventPublisher;
    
    
    @BeforeEach
    void before() {
        CatalogContentEntity mockContent = CatalogContentEntity.builder().build();
        RatingEntity mockRating = mock(RatingEntity.class);
        when(contentRepository.findByContentId(anyString()))
                .thenReturn(Optional.of(mockContent));
        when(ratingRepository.save(any(RatingEntity.class))).thenReturn(mockRating);
        addRatingHandler = new DefaultAddRatingHandler(ratingRepository, contentEventPublisher, contentRepository);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    @DisplayName("점수는 1에서 10점까지 줄 수 있다.")
    void scoreTest(Integer score) throws ContentNotFoundException{
        // given
        
        AddRating command = AddRating.builder().score(score).build();
    
        // when
        
        assertThatThrownBy(() -> addRatingHandler.handle(command));
        
        // then
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-100, -33333, 0, 11, 999})
    @DisplayName("0, 음수나 10 초과 값은 예외를 발생시킨다.")
    void invalidValueTest(Integer score) throws ContentNotFoundException {
        // given

        AddRating command = AddRating.builder().score(score).build();
        
        // when
        assertThatThrownBy(() -> addRatingHandler.handle(command)).isInstanceOf(InvalidRatingScoreException.class);
        
        // then
    }
    
}