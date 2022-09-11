package com.recody.recodybackend.movie.features.searchmovies.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.json.JsonVisitorStream;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesUsingApiResponse;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class SearchMoviesUsingTMDBApiResponse implements SearchMoviesUsingApiResponse {
    private static final MovieSource source = MovieSource.TMDB;
    private static final String RESULTS = "results";
    private static final String GENRE_IDS = "genre_ids";
    private static final String ID = "id";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    
    private final JsonNode jsonNode;
    
    @Override
    public Map<Integer, List<Integer>> getGenreIdsMap() {
        Iterator<JsonNode> iterator = movieIterator();
        int size = jsonNode.get(RESULTS).size();
        log.debug("RESULT size: {}", size);
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        while (iterator.hasNext()) {
            JsonNode movieNode = iterator.next();
            map.put(getCurrentId(movieNode), getCurrentGenreIds(movieNode));
        }
        return map;
    }
    
    @Override
    public List<Integer> getMovieIds() {
        log.debug("movie Id 를 찾는 중");
        return JsonVisitorStream
                .of(jsonNode)
                .go(RESULTS)
                .whichIsArray()
                .andIterate()
                .whereNameIs(ID)
                .whichAreIntegers()
                .collect();
    }
    
    @Override
    public MovieSource getContentSource() { return source; }
    
    
    @Override
    public Integer getMovieId(int n) {
        return jsonNode.withArray(RESULTS).get(n).get(ID).asInt();
    }
    
    @Override
    public String getOriginalLanguage(int n) {
        return jsonNode.withArray(RESULTS).get(n).get(ORIGINAL_LANGUAGE).asText();
    }
    
    @Override
    public String getOriginalTitle(int n) {
        return jsonNode.withArray(RESULTS).get(n).get(ORIGINAL_TITLE).asText();
    }
    
    @Override
    public String getOverview(int n) {
        return jsonNode.withArray(RESULTS).get(n).get(OVERVIEW).asText();
    }
    
    @Override
    public String getPosterPath(int n) {
        return jsonNode.withArray(RESULTS).get(n).get(POSTER_PATH).asText();
    }
    
    @Override
    public String getReleaseDate(int n) {
        return jsonNode.withArray(RESULTS).get(n).get(RELEASE_DATE).asText();
    }
    
    @Override
    public String getTitle(int n) {
        return jsonNode.withArray(RESULTS).get(n).get(TITLE).asText();
    }
    
    @Override
    public int size() {
        return jsonNode.withArray(RESULTS).size();
    }
    
    private Iterator<JsonNode> movieIterator() {
        return jsonNode.get(RESULTS).iterator();
    }
    
    private List<Integer> getCurrentGenreIds(JsonNode movieNode) {
        log.debug("finding current node's GenreIds: {}", movieNode);
        return JsonVisitorStream.of(movieNode).go(GENRE_IDS).whichIsArrayOf().integers().collect();
    }
    
    private int getCurrentId(JsonNode movieNode) {
        return movieNode.get(ID).asInt();
    }
}
