package com.recody.recodybackend.catalog.features.personalize;

import com.recody.recodybackend.catalog.PersonalizedMovie;
import com.recody.recodybackend.catalog.PersonalizedMovieMapper;
import com.recody.recodybackend.movie.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MoviePersonalizer implements ContentPersonalizer<Movie, PersonalizedMovie> {
    
    private final PersonalizedMovieMapper personalizedMovieMapper;
    
    @Override
    public PersonalizedMovie personalize(Movie content, Long userId) {
        PersonalizedMovie personalizedMovie = personalizedMovieMapper.map(content);
        
        personalizedMovie.setCategory(content.getCategory());
        personalizedMovie.setPersonalizedUserId(userId);
        
        return personalizedMovie;
    }
}
