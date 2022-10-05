package com.recody.recodybackend.movie.features.resolvegenre.fromapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.openapi.APIRequester;
import com.recody.recodybackend.common.openapi.JsonAPIResponse;
import com.recody.recodybackend.movie.features.recognize.MovieGenreManager;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
class GetMovieGenreFromTMDBApiHandler implements com.recody.recodybackend.movie.features.resolvegenres.fromapi.GetMovieGenreFromTMDBApiHandler {
    
    private static final String GENRES = "genres";
    private static final String ID = "id";
    private static final String NAME = "name";
    private final APIRequester<TMDBAPIRequest> apiRequester;
    private final Map<Integer, String> tmdbGenreMap = new HashMap<>();
    private boolean hasGenres = false;
    private final MovieGenreManager movieGenreManager;
    
    public GetMovieGenreFromTMDBApiHandler(APIRequester<TMDBAPIRequest> apiRequester,
                                           MovieGenreManager movieGenreManager) {
        this.apiRequester = apiRequester;
        this.movieGenreManager = movieGenreManager;
    }
    
    public boolean hasGenres() {
        return hasGenres;
    }
    
    @Override
    @PostConstruct
    public void initTmdbGenre() {
        TMDBGenreListFeature request = new TMDBGenreListFeature();
        JsonAPIResponse response = apiRequester.requestToJson(request);
        try {
            response.rawStream().map(jsonNode -> {
                List<JsonNode> parents = jsonNode.withArray(GENRES).findParents(ID);
                HashMap<Integer, String> map = new HashMap<>();
                for (JsonNode parent : parents) {
                    JsonNode idField = parent.get(ID);
                    JsonNode nameField = parent.get(NAME);
                    tmdbGenreMap.put(idField.asInt(), nameField.textValue());
                }
                return map;
            }).findAny()
                    .orElseThrow(() -> new RuntimeException("json 스트림을 처리하지 못했습니다."));
            this.hasGenres = true;
            log.info("Initiated Genres From TMDB API");
        } catch (Exception exception){
            log.warn("exception: {}", exception.getMessage());
            this.hasGenres = false;
        }
    
        for (Integer key : tmdbGenreMap.keySet()) {
            MovieGenre genre = new MovieGenre(key, tmdbGenreMap.get(key));
            genre.setSource(MovieSource.TMDB);
            String genreId = movieGenreManager.register(genre);
        }
    }
    
    @Override
    public MovieGenre getMovieGenre(Integer tmdbGenreId) {
        String genreName = tmdbGenreMap.get(tmdbGenreId);
        if (genreName == null) {
            // TMDB 장르 맵에 없음.
            log.warn("TMDB Genre Map 에서 장르를 찾을 수 없었습니다. tmdbGenreId: {}", tmdbGenreId);
        }
        log.trace("for TMDB Genre Id: {},  GenreName: {}", tmdbGenreId, genreName);
        return new MovieGenre(tmdbGenreId, genreName);
    }
}
