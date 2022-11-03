package com.recody.recodybackend.movie.features.getmoviecredit;

import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
import com.recody.recodybackend.movie.RecodyMovieApplication;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.title.MovieTitleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles( "test" )
@ContextConfiguration( classes = RecodyMovieApplication.class )
class TMDBGetMovieCreditHandlerTest {
    
    public static final Integer MOVIE_ID = 705996;

    @Autowired
    private GetMovieCreditHandler getMovieCreditHandler;
    
    @Autowired
    private MovieRepository movieRepository;
    
    
    
    @BeforeEach
    void before() {
        MovieEntity movieEntity = MovieEntity.builder().tmdbId(MOVIE_ID).build();
        MovieTitleEntity titleEntity = MovieTitleEntity.builder().build();
        movieEntity.setTitle(titleEntity);
        MovieEntity saved = movieRepository.save(movieEntity);
    }
    @Test
    void apiTest() {
        // given
        GetMovieCredit command = new GetMovieCredit(MOVIE_ID);
    
        // when
        GetMovieCreditResult result = getMovieCreditHandler.handle(command);
        
        // then
        
        List<Actor> actors = result.getActors();
        List<Director> directors = result.getDirectors();
        assertThat(actors).isNotEmpty();
        assertThat(directors).isNotEmpty();
        System.out.println("actors = " + actors);
        System.out.println("directors = " + directors);
        
    }
    
}