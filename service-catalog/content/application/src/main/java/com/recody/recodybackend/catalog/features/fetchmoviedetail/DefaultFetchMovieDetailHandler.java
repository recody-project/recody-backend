package com.recody.recodybackend.catalog.features.fetchmoviedetail;

import com.recody.recodybackend.movie.MovieDetailViewModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultFetchMovieDetailHandler implements FetchMovieDetailHandler {
    @Value("${catalog.movie.search.base-url}")
    private String baseUrl;
    @Value("${catalog.movie.access-token}")
    private String bearerToken;
    @Getter
    private static final String path = "/api/v1/movie/detail";
    @Getter
    private static final String MovieId ="movieId";
    private static final String LANGUAGE = "language";
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    
    @Override
    public MovieDetailViewModel handle(FetchMovieDetail command) {
        log.debug("handling command: {}", command);
        Integer tmdbId = command.getMovieId();
        String language = command.getLanguage();
    
        URI uri = makeUrl(tmdbId, language);
        HttpHeaders httpHeaders = makeAuthorizedHeaders();
        RequestEntity<Void> requestEntity = RequestEntity.get(uri).headers(httpHeaders).build();
    
        MovieDetailViewModel movieDetail;
        try {
            movieDetail = restTemplate.exchange(requestEntity, MovieDetailViewModel.class).getBody();
        } catch (RestClientException exception){
            log.warn("exception: {}", exception.getMessage());
            throw new RuntimeException();
        }
        log.info("영화 정보를 Movie 서비스에서 가져왔습니다.");
        return movieDetail;
    }
    
    private HttpHeaders makeAuthorizedHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);
        return httpHeaders;
    }
    
    private URI makeUrl(Integer tmdbId, String language) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                                   .path(path)
                                   .queryParam(MovieId, tmdbId)
                                   .queryParam(LANGUAGE, language)
                                   .encode()
                                   .build()
                                   .toUri();
    }
}
