package com.recody.recodybackend.movie.features.getmoviecredit;

import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBGetMovieCreditResponse;
import com.recody.recodybackend.movie.features.tmdb.TMDBMovieID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
class TMDBFetchMovieCreditsHandler implements FetchMovieCreditsHandler<TMDBGetMovieCreditResponse, TMDBMovieID>{
    private static final String TMDB_BASE_URI = "https://api.themoviedb.org/3";
    public static final String path = "/movie/";
    public static final String path2 = "/credits";
    private final String API_KEY_PARAM_NAME = "api_key";
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${movie.tmdb.api-key}")
    private String apiKey;
    
    @Override
    public TMDBGetMovieCreditResponse handle(TMDBMovieID command) {
        log.debug("handling command: {}", command);
        Integer movieId = command.getId();
        TMDBGetMovieCreditResponse response;
        URI url = makeUrl(movieId);
        RequestEntity<Void> requestEntity = RequestEntity.get(url).build();
        try {
            response = restTemplate.exchange(requestEntity, TMDBGetMovieCreditResponse.class).getBody();
            // null check
            // TODO null check 메시지 수정
            Objects.requireNonNull(response);
            log.info("TMDB 에서 credit 정보를 가져왔습니다.");
            return response;
        } catch (RestClientException exception4xx) {
            log.warn("exception4xx: {}", exception4xx.getMessage());
            throw new RuntimeException();
        }
    }
    private URI makeUrl(Integer movieId) {
        return UriComponentsBuilder.fromUriString(TMDB_BASE_URI)
                                   .path(path + movieId + path2)
                                   .queryParam(API_KEY_PARAM_NAME, apiKey)
                                   .encode()
                                   .build()
                                   .toUri();
    }
}
