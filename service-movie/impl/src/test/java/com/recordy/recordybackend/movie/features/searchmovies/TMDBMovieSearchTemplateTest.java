package com.recordy.recordybackend.movie.features.searchmovies;

import com.recordy.recordybackend.RecordyMovieApplication;
import com.recordy.recordybackend.movie.features.searchmovies.request.TMDBMovieSearchRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
@ContextConfiguration(classes = RecordyMovieApplication.class)
class TMDBMovieSearchTemplateTest {
    
    @Autowired private MovieSearchTemplate template;
    
    @Test
    @DisplayName("영화 이름과 언어를 설정하여 검색할 수 있다.")
    void test01() {
        // given
        
        // when
        String result = template.execute(TMDBMovieSearchRequest.builder()
                                                               .movieName("결심")
                                                               .korean().build());
        // then
        assertThat(result).isNotNull();
    }
    
    @Test
    @DisplayName("영화 이름을 지정하지 않으면 예외가 발생한다.")
    void movieName() {
        // given
        
        // when
        TMDBMovieSearchRequest request = TMDBMovieSearchRequest.builder().korean().build();
        // then
        assertThatThrownBy(() -> template.execute(request)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("언어을 지정하지 않으면 예외가 발생한다.")
    void language() {
        // given
        
        // when
        TMDBMovieSearchRequest request = TMDBMovieSearchRequest.builder().movieName("하하").build();
    
        // then
        assertThatThrownBy(() -> template.execute(request)).isInstanceOf(IllegalArgumentException.class);
    }
}