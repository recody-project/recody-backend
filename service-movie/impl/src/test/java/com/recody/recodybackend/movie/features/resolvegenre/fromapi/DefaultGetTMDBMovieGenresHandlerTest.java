package com.recody.recodybackend.movie.features.resolvegenre.fromapi;

import com.recody.recodybackend.movie.RecodyMovieApplication;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyMovieApplication.class)
class DefaultGetTMDBMovieGenresHandlerTest {
    
    @Autowired
    GetTMDBMovieGenresHandler getTMDBMovieGenresHandler;
    
    @Test
    void getTMDBMovieGenres() {
    
        List<TMDBMovieGenre> tmdbMovieGenres = getTMDBMovieGenresHandler.getTMDBMovieGenres();
    
        assertThat(tmdbMovieGenres).isNotEmpty();
        System.out.println("tmdbMovieGenres = " + tmdbMovieGenres);
        
    }
}