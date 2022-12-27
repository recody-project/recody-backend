package com.recody.recodybackend.movie.features.resolvegenre.fromapi;

import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieGenre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
class DefaultGetTMDBMovieGenresHandler implements GetTMDBMovieGenresHandler {
    private static final String baseUrl = "https://api.themoviedb.org/3";
    private static final String GENRE_PATH = "/genre/movie/list";
    public static final String API_KEY_PARAM_NAME = "api_key";
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${movie.tmdb.api-key}")
    private String apiKey;
    
    @Override
    public List<TMDBMovieGenre> getTMDBMovieGenres() {
        URI url = makeUrl();
        RequestEntity<Void> request = RequestEntity.get(url).build();
        try {
            TMDBGetGenresResponse response = restTemplate.exchange(request, TMDBGetGenresResponse.class).getBody();
            List<TMDBMovieGenre> genres = Objects.requireNonNull(response, "TMDB 에서 장르를 받아오지 못했습니다.")
                                            .getGenres();
            log.info("Initiated Genres From TMDB API");
            return genres;
        } catch (Exception exception) {
            log.warn("받아오지 못했습니다. exception: {}", exception.getMessage());
            return List.of();
        }
    }
    
    private URI makeUrl() {
        return UriComponentsBuilder.fromUriString(baseUrl)
                                   .path(GENRE_PATH)
                                   .queryParam(API_KEY_PARAM_NAME, apiKey)
                                   .encode()
                                   .build()
                                   .toUri();
    }
}
