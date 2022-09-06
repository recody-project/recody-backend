package com.recody.recodybackend.movie.features.searchmovies;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.RecodyMovieApplication;
import com.recody.recodybackend.movie.features.searchmovies.request.JsonTMDBMovieSearchResult;
import com.recody.recodybackend.movie.features.searchmovies.request.MovieSearchResult;
import com.recody.recodybackend.movie.features.searchmovies.request.MovieSearchTemplate;
import com.recody.recodybackend.movie.features.searchmovies.request.TMDBMovieSearchRequestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Iterator;
import java.util.Locale;

@SpringBootTest
@ActiveProfiles("local")
@ContextConfiguration(classes = RecodyMovieApplication.class)
class JsonTMDBMovieSearchMapperTest {
    
    private final JsonTMDBMovieSearchMapper mapper = new JsonTMDBMovieSearchMapper();
    @Autowired private MovieSearchTemplate template;
    
    @Test
    @DisplayName("test01")
    void test01() {
        // given
        MovieSearchResult result = template.executeToJson(TMDBMovieSearchRequestEntity.builder().language("ko").movieName("결심").build());
        // when
        // 장르 세팅
    
        // root id 세팅
    
        // 요청 언어 세팅
        SearchMovieResponse response = mapper.dynamicMapper(result).requestedLanguage(Locale.KOREA)
                                         .genreIds(null).contentRoot(null).get();
        System.out.println(response);
    
        // then
    }
    
    @Test
    @DisplayName("jsonNodeTest")
    void jsonNodeTest() {
        // given
        MovieSearchResult movieSearchResult = template.executeToJson(TMDBMovieSearchRequestEntity.builder()
                                                                                                 .language("ko")
                                                                                                 .movieName("결심")
                                                                                                 .build());
        JsonTMDBMovieSearchResult result = (JsonTMDBMovieSearchResult) movieSearchResult;
        JsonNode response = result.getResponse();
        // when
        Iterator<JsonNode> results = response.withArray("results").iterator();
        JsonNode id = results.next().get("id");
        System.out.println(id);
    
        // then
    }
    
}