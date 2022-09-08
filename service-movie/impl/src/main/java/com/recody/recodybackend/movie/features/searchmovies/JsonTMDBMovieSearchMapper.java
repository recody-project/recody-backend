package com.recody.recodybackend.movie.features.searchmovies;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.movie.features.searchmovies.request.JsonTMDBMovieSearchResult;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
class JsonTMDBMovieSearchMapper implements MovieSearchMapper {
    
    // TODO VO로 만들기?
    //      TMDB에만 해당되는 정보이므로 굳이 할 필요 없을 수도.
    //      단, JsonNode 이외의 형태로 받아오는 경우도 고려한다면 의미가 있을 수도 있다.
    /* TMDB response keys */
    private static final String RESULTS = "results";
    private static final String GENRE_IDS = "genre_ids";
    private static final String ID = "id";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String POSTER_PATH_PREFIX = "https://image.tmdb.org/t/p/w500";
    
    
    @Override
    public SearchMovieResponse map(Object object) {
        JsonNode jsonNode = (JsonNode) object;
        JsonNode results = jsonNode.withArray(RESULTS);
        ArrayList<SingleMovieSpec> specs = new ArrayList<>();
        for (JsonNode result : results) {
            specs.add(doMap(result));
        }
        return SearchMovieResponse.builder()
                       .movies(specs).build();
    }
    
    /*
    * 모든 매핑 로직이 새로운 객체에서 이루어진다.
    * TODO 비동기 or 멀티 쓰레딩으로 가능? */
    public DynamicMapper dynamicMapper(Object object){
        JsonTMDBMovieSearchResult result = (JsonTMDBMovieSearchResult) object;
        JsonNode json = result.getResponse();
        SearchMovieResponse response = map(json);
        return new TMDBDynamicMapper(response);
    }
    
    private SingleMovieSpec doMap(JsonNode results) {
        log.debug("results: {}", results);
        return SingleMovieSpec.builder()
                              .movieId(getInt(results, ID))
                              .originalLanguage(getString(results, ORIGINAL_LANGUAGE))
                              .originalTitle(getString(results, ORIGINAL_TITLE))
                              .overview(getString(results, OVERVIEW))
                              .posterPath(POSTER_PATH_PREFIX + getString(results, POSTER_PATH))
                              .releaseDate(getString(results, RELEASE_DATE))
                              .title(getString(results, TITLE))
                              .source(MovieSource.TMDB).build();
    }
    
    private String getString(JsonNode results, String key) {
        String value = results.get(key).asText();
        log.trace("value: {}", value);
        return value;
    }
    
    private int getInt(JsonNode results, String key) {
        int value = results.get(key).asInt();
        log.trace("value: {}", value);
        return value;
    }
}
