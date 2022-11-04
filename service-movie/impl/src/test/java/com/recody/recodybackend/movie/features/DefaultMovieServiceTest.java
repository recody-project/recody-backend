package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.RecodyMovieApplication;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.web.SearchMoviesResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyMovieApplication.class)
class DefaultMovieServiceTest {
    
    public static final String LANGUAGE = "en";
    public static final String TITLE = "bar";
    @Autowired
    MovieSearchService movieSearchService;
    
    @Autowired
    MovieRepository movieQueryRepository;
    
    @Test
    void searchMoviesByQuery() throws InterruptedException {
        SearchMoviesResult result = movieSearchService.searchMovies(
                SearchMovies.builder().movieName(TITLE).language(LANGUAGE).build());
        
        // 저장될 때 까지 현재 쓰레드에서 기다린다.
        Thread.sleep(1000L);
        
        System.out.println("result = " + result);
        
        List<MovieEntity> queriedMovies = movieQueryRepository.findByTitleLike(TITLE, Locale.forLanguageTag(LANGUAGE));
        
        System.out.println("queriedMovies = " + queriedMovies.size());
        
        assertThat(queriedMovies).isNotEmpty();
    }
}