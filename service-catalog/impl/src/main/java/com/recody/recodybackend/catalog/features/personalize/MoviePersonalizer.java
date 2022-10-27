package com.recody.recodybackend.catalog.features.personalize;

import com.recody.recodybackend.catalog.features.PersonalizedMovie;
import com.recody.recodybackend.catalog.features.PersonalizedMovieMapper;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class MoviePersonalizer implements ContentPersonalizer<Movie, PersonalizedMovie> {
    
    private final PersonalizedMovieMapper personalizedMovieMapper;
    
    @Override
    public PersonalizedMovie personalize(Movie content, Long userId) {
        PersonalizedMovie personalizedMovie = personalizedMovieMapper.map(content);
        
        // 개인화 로직 현재 없음
        BasicCategory category = content.getCategory();
        log.debug("BasicCategory, category: {}", category);
        personalizedMovie.setCategory(category);
        personalizedMovie.setPersonalizedUserId(userId);
        
        return personalizedMovie;
    }
}
