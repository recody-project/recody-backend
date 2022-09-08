package com.recody.recodybackend.movie.features.searchmovies.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Stream;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class TMDBMovieSearchResponse implements MovieSearchResponse {
    private static final MovieSource source = MovieSource.TMDB;
    private static final String RESULTS = "results";
    private static final String GENRE_IDS = "genre_ids";
    private static final String ID = "id";
    private final JsonNode response;
    private final Locale requestLocale;
    
    private Iterator<JsonNode> movieIterator() {
        return response.get(RESULTS).iterator();
    }
    
    @Override
    public Map<Integer, List<Integer>> getGenreIdsMap(){
        Iterator<JsonNode> iterator = movieIterator();
        int size = response.get(RESULTS).size();
        log.info("RESULT size: {}", size);
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        while (iterator.hasNext()){
            JsonNode targetNode = iterator.next();
            map.put(getCurrentId(targetNode), getCurrentGenreIds(targetNode));
        }
        return map;
    }
    
    @Override
    public List<Integer> getMovieIds(){
        Iterator<JsonNode> iterator = movieIterator();
        ArrayList<Integer> movieIds = new ArrayList<>();
        while (iterator.hasNext()){
            JsonNode currentNode = iterator.next();
            movieIds.add(getCurrentId(currentNode));
        }
        return movieIds;
    }
    
    @Override
    public MovieSource getContentSource() {
        return source;
    }
    
    @Override
    public Locale getLocale() {
        return this.requestLocale;
    }
    
    private List<Integer> getCurrentGenreIds(JsonNode movieNode) {
        return Stream.of(movieNode.withArray(GENRE_IDS).iterator())
                        .map(this::flattenToList)
                        .peek(ids -> log.debug("ids: {}", ids))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("장르 id가 없습니다."));
    }
    
    private ArrayList<Integer> flattenToList(Iterator<JsonNode> iterator) {
        ArrayList<Integer> ids = new ArrayList<>();
        while (iterator.hasNext()) { ids.add(iterator.next().asInt()); }
        return ids;
    }
    
    private int getCurrentId(JsonNode node) {
        return node.get(ID).asInt();
    }
}
