package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.RecodyMovieApplication;
import com.recody.recodybackend.movie.features.searchmovies.request.MovieSearchResponse;
import com.recody.recodybackend.movie.features.searchmovies.request.MovieSearchTemplate;
import com.recody.recodybackend.movie.features.searchmovies.request.TMDBMovieSearchRequestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyMovieApplication.class)
class JsonTMDBMovieSearchMapperTest {
    
    private final JsonTMDBMovieSearchMapper mapper = new JsonTMDBMovieSearchMapper();
    @Autowired private MovieSearchTemplate template;
    
    @Test
    @DisplayName("장르 id 정보가 없으면 예외 발생 ")
    void test01() {
        // given
        MovieSearchResponse result = template.executeToJson(TMDBMovieSearchRequestEntity.builder().language("ko").movieName("결심").build());
        // when
        // 장르 세팅
    
        // root id 세팅
    
        // 요청 언어 세팅
        assertThatThrownBy(() -> {
            mapper.dynamicMapper(result).requestedLanguage(Locale.KOREA).genreIds(null).contentRoot(null).get();
        }).isInstanceOf(RuntimeException.class);
        // then
    }
    
}