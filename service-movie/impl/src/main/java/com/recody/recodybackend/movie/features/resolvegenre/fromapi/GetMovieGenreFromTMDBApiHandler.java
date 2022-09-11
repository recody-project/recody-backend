package com.recody.recodybackend.movie.features.resolvegenre.fromapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.openapi.APIRequester;
import com.recody.recodybackend.common.openapi.JsonAPIResponse;
import com.recody.recodybackend.movie.features.resolvegenres.fromapi.GetMovieGenreFromTMDBApi;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
class GetMovieGenreFromTMDBApiHandler implements GetMovieGenreFromTMDBApi {
    
    private static final String GENRES = "genres";
    private static final String ID = "id";
    private static final String NAME = "name";
    private final APIRequester<TMDBAPIRequest> apiRequester;
    private final Map<Integer, String> tmdbGenreMap = new HashMap<>();
    private boolean hasGenres = false;
    
    public GetMovieGenreFromTMDBApiHandler(APIRequester<TMDBAPIRequest> apiRequester) {
        this.apiRequester = apiRequester;
    }
    
    public boolean hasGenres() {
        return hasGenres;
    }
    
    @Override
    @PostConstruct
    public void initTmdbGenre() {
        TMDBGenreAPIRequest request = new TMDBGenreAPIRequest();
        JsonAPIResponse response = apiRequester.executeToJson(request);
        try {
            response.rawStream().map(jsonNode -> {
                List<JsonNode> parents = jsonNode.withArray(GENRES).findParents(ID);
                HashMap<Integer, String> map = new HashMap<>();
                for (JsonNode parent : parents) {
                    JsonNode idField = parent.get(ID);
                    JsonNode nameField = parent.get(NAME);
                    tmdbGenreMap.put(idField.asInt(), nameField.textValue());
                }
                log.info("map: {}", map);
                return map;
            }).findAny()
                    .orElseThrow(() -> new RuntimeException("json 스트림을 처리하지 못했습니다."));
            this.hasGenres = true;
            log.info("Fetched genreMap: {}", tmdbGenreMap);
        } catch (Exception exception){
            log.warn("exception: {}", exception.getMessage());
            this.hasGenres = false;
        }
    }
    
    @Override
    public MovieGenre getMovieGenre(Integer tmdbGenreId) {
        String genreName = tmdbGenreMap.get(tmdbGenreId);
        if (genreName == null) {
            // TMDB 장르 맵에 없음.
            log.warn("TMDB Genre Map 에서 장르를 찾을 수 없었습니다. tmdbGenreId: {}", tmdbGenreId);
        }
        log.debug("for TMDB Genre Id: {},  GenreName: {}", tmdbGenreId, genreName);
        // TODO: database 에 업데이트 하기 <Message>
        return new MovieGenre(tmdbGenreId, genreName);
    }
}
