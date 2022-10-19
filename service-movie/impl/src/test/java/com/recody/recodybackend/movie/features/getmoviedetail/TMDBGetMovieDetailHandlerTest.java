package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.RecodyMovieApplication;
import com.recody.recodybackend.movie.general.MovieSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyMovieApplication.class)
class TMDBGetMovieDetailHandlerTest {
    
    public static final String MOVIE_ID = "705996";
    @Autowired GetMovieDetailHandler getMovieDetailHandler;
    @Autowired MovieDetailMapper movieDetailMapper;
    
    @Test
    @DisplayName("매핑이 잘 이루어지는가?")
    void test01() {
        // given
        GetMovieDetail command = new GetMovieDetail("705996", "ko");
        MovieDetail detail = getMovieDetailHandler.handle(command);
    
        // when
        
        // then
        assertThat(detail.getSource()).isEqualTo(MovieSource.TMDB);
        assertThat(detail.getTmdbId()).isEqualTo(Integer.parseInt(MOVIE_ID));
    }
}