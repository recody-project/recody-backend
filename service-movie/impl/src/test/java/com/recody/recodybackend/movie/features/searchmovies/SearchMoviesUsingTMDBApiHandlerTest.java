package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.RecodyMovieApplication;
import com.recody.recodybackend.movie.features.searchmovies.request.SearchMoviesUsingTMDBApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyMovieApplication.class)
class SearchMoviesUsingTMDBApiHandlerTest {
    
    @Autowired private SearchMoviesUsingApiHandler template;
    
    @Test
    @DisplayName("영화 이름과 언어를 설정하여 검색할 수 있다.")
    void test01() {
        // given
        
        // when
        String result = template.handleToString(SearchMoviesUsingTMDBApi.builder()
                                                                        .movieName("결심")
                                                                        .korean().build());
        // then
        assertThat(result).isNotNull();
    }
    
    @Test
    @DisplayName("영화 이름을 지정하지 않으면 예외가 발생한다.")
    void movieName() {
        // given
        
        // then
        assertThatThrownBy(() -> SearchMoviesUsingTMDBApi.builder().korean().build()).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("언어는 지정하지 않아도 예외가 발생하지 않는다. ")
    void language() {
        // given
        
        // when
        SearchMoviesUsingTMDBApi request = SearchMoviesUsingTMDBApi.builder().movieName("하하").build();
    
        // then
        assertThatNoException().isThrownBy(() -> template.handleToString(request));
    }
}