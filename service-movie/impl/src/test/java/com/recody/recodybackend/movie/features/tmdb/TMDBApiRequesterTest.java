package com.recody.recodybackend.movie.features.tmdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.RecodyMovieApplication;
import com.recody.recodybackend.common.openapi.ApiRequester;
import com.recody.recodybackend.common.openapi.JsonApiResponse;
import com.recody.recodybackend.movie.features.searchmovies.request.NewTMDBMovieSearchRequestEntity;
import com.recody.recodybackend.movie.general.TMDBRequestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
@ContextConfiguration(classes = RecodyMovieApplication.class)
class TMDBApiRequesterTest {
    
    @Autowired private ApiRequester<TMDBRequestEntity> requester;
    
    @Test
    @DisplayName("test01")
    void test01() {
        // given
        NewTMDBMovieSearchRequestEntity ko = new NewTMDBMovieSearchRequestEntity("결심", "ko");
        // when
        JsonApiResponse jsonApiResponse = requester.executeToJson(ko);
        JsonNode jsonNode = requester.executeToJsonNode(ko);
    
        // then
        List<String> results = jsonApiResponse.stream()
                                              .goArray("results")
                                              .iterate()
                                              .each("backdrop_path").theyAreStrings().collect();
        assertThat(results.size()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("test02")
    void test02() {
        // given
        NewTMDBMovieSearchRequestEntity ko = new NewTMDBMovieSearchRequestEntity("결심", "ko");
        // when
        JsonApiResponse jsonApiResponse = requester.executeToJson(ko);
        
        JsonNode jsonNode = requester.executeToJsonNode(ko);
        
        
        int string = jsonApiResponse.stream()
                                    .go("page")
                                    .itIsInteger()
                                    .get();
        List<String> strings = jsonApiResponse.stream()
                                              .goArray("results")
                                              .iterate()
                                              .each("original_language")
                                              .theyAreStrings().collect();
    
        List<String> map = jsonApiResponse.stream()
                                          .goArray("results")
                                          .iterate()
                                          .each("original_language")
                                          .theyAreStrings().collect();
    
        List<Integer> integers = jsonApiResponse.stream()
                                                .goArray("results")
                                                .iterate()
                                                .each("genre_ids")
                                                .theyAreArraysOf()
                                                .integers()
                                                .collect();
    
        List<Integer> integers2 = jsonApiResponse.stream()
                                                .go("results")
                                                .itIsArray()
                                                .iterate()
                                                .each("genre_ids")
                                                .theyAreArraysOf()
                                                .integers()
                                                .collect();
        System.out.println("strings = " + strings);
        System.out.println("map = " + map);
    
        assertThat(string).isEqualTo(1);
    
        // then
    }

}