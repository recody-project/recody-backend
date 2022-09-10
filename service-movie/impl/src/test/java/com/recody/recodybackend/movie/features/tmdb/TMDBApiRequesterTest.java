package com.recody.recodybackend.movie.features.tmdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.RecodyMovieApplication;
import com.recody.recodybackend.common.openapi.ApiRequester;
import com.recody.recodybackend.common.openapi.JsonApiResponse;
import com.recody.recodybackend.movie.features.test.NewTMDBMovieDetailRequest;
import com.recody.recodybackend.movie.features.test.NewTMDBMovieSearchRequestEntity;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.TMDBRequestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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
        List<String> results = jsonApiResponse.visitorStream()
                                              .goArray("results")
                                              .andIterate()
                                              .whereNameIs("backdrop_path")
                                              .whichAreStrings()
                                              .collect();
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
    
    
        int string = jsonApiResponse.visitorStream().go("page").whichIsInteger().get();
        List<String> strings = jsonApiResponse.visitorStream()
                                              .goArray("results")
                                              .andIterate()
                                              .whereNameIs("original_language")
                                              .whichAreStrings()
                                              .collect();
    
        List<String> map = jsonApiResponse.visitorStream()
                                          .goArray("results")
                                          .andIterate()
                                          .whereNameIs("original_language")
                                          .whichAreStrings()
                                          .collect();
    
        List<Integer> integers = jsonApiResponse.visitorStream()
                                                .goArray("results")
                                                .andIterate()
                                                .whereNameIs("genre_ids")
                                                .whichIsArrayOf()
                                                .integers()
                                                .collect();
    
        List<Integer> integers2 = jsonApiResponse.visitorStream()
                                                 .go("results")
                                                 .whichIsArray()
                                                 .andIterate()
                                                 .whereNameIs("genre_ids")
                                                 .whichIsArrayOf()
                                                 .integers()
                                                 .collect();
    
        System.out.println("strings = " + strings);
        System.out.println("map = " + map);
    
        assertThat(string).isEqualTo(1);
    
        // then
    }
    
    @Test
    @DisplayName("test03")
    void test03() {
        // given
        NewTMDBMovieDetailRequest request = new NewTMDBMovieDetailRequest("705996", "ko");
        // when
        JsonApiResponse jsonApiResponse = requester.executeToJson(request);
    
        List<MovieGenre> movieGenres = jsonApiResponse
                .visitorStream()
                .go("genres")
                .whichIsArray()
                .andIterate()
                .whereNamesAre("id", "name")
                .whichTypesAre(Integer.class, String.class)
                .collectInto(MovieGenre.class);
    
        System.out.println(movieGenres);
        assertThat(movieGenres.get(0)).isInstanceOf(MovieGenre.class);
        // then
    }
    
    @Test
    @DisplayName("test04")
    void test04() {
        // given
        NewTMDBMovieSearchRequestEntity ko = new NewTMDBMovieSearchRequestEntity("결심", "ko");
        NewTMDBMovieDetailRequest request = new NewTMDBMovieDetailRequest("705996", "ko");
        // when
    
        JsonNode jsonNode = requester.executeToJsonNode(request);
        // when
    
        boolean genres = jsonNode.get("genres").isObject();
        assertThat(genres).isFalse();
    
        List<MovieGenre> collect = jsonNode.withArray("genres")
                                           .findParents("id")
                                           .stream().map(node -> {
                                        int id = node.get("id").asInt();
                                        String name = node.get("name").asText();
                                        return new MovieGenre(id, name);
                                    }).collect(Collectors.toList());
        
        
        System.out.println(collect);
    
        // then
    }
    
}