package com.recordy.recordybackend.movie.features.searchmovies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TMDBMovieSearchTemplateTest {
    
    private final TMDBMovieSearchTemplate template = new TMDBMovieSearchTemplate();
    
    @Test
    @DisplayName("영화 이름과 언어를 설정하여 검색할 수 있다.")
    void test01() {
        // given
        String result = template.movieName("결심").language("ko-KR").execute();
        // when
        
        // then
        assertThat(result).isNotNull();
        
    }
}