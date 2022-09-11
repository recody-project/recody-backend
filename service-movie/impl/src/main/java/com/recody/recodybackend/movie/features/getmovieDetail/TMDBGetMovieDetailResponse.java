package com.recody.recodybackend.movie.features.getmovieDetail;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.openapi.JsonAPIResponse;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TMDBGetMovieDetailResponse implements GetMovieDetailResponse {
    
    private static final String GENRES = "genres";
    private static final String IMDB_ID = "imdb_id";
    
    private final JsonAPIResponse response;
    
    public TMDBGetMovieDetailResponse(JsonAPIResponse response) { this.response = response; }
    
    @Override
    public MovieSource getContentSource() { return MovieSource.TMDB; }
    
    @Override
    public List<MovieGenre> getGenres() {
        return response.rawStream().map(node -> {
            JsonNode jsonNode = node.get(GENRES); // go, itIsArray
            ArrayList<MovieGenre> genres = new ArrayList<>(); // iterate
            for (JsonNode genreNode : jsonNode) {
                genres.add(new MovieGenre(genreNode.get("id").asInt(),
                                          genreNode.get("name").asText()));
            }
            log.debug("genres: {}", genres);
            return genres;
        }).findAny().orElseThrow(() -> new RuntimeException("스트림 처리 실패 "));
    }
    
    @Override
    public String getImdbId() {
        return response.visitorStream().go(IMDB_ID).whichIsText().get();
    }
}
