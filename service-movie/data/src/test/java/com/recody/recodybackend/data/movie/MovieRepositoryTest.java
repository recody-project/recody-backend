package com.recody.recodybackend.data.movie;

import com.recody.recodybackend.movie.data.MovieDataConfiguration;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.title.MovieTitleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = MovieDataConfiguration.class)
class MovieRepositoryTest {
    
    @Autowired
    MovieRepository movieRepository;
    
    @Test
    void saveTest() {
        // given
        MovieTitleEntity title = MovieTitleEntity.builder().build();
        MovieEntity entity = MovieEntity.builder().tmdbId( 1 ).build();
        entity.setTitle(title);
        MovieEntity saved = movieRepository.save(entity);
    
        // when
    
        assertThat(saved).isNotNull();
        System.out.println("saved = " + saved);
        
        // then
    }
    
}