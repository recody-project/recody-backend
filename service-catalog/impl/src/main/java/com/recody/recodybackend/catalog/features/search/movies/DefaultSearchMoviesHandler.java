package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;
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
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSearchMoviesHandler implements SearchMoviesHandler{
    
    @Value("${catalog.movie.search.base-url}")
    private String baseUrl;
    
    @Value("${catalog.movie.access-token}")
    private String bearerToken;
    private static final String path = "/api/v1/movie/search";
    private static final String MOVIE_SEARCH_PARAM_NAME = "movieName";
    private static final String LANGUAGE_PARAM_NAME = "language";
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public SearchMoviesResult handle(SearchMovies command) {
        log.debug("handling command: {}", command);
        String keyword = command.getKeyword();
        String language = command.getLanguage();
        URI uri = makeUrl(keyword, language);
        HttpHeaders httpHeaders = makeAuthorizedHeaders();
        RequestEntity<Void> requestEntity = RequestEntity.get(uri).headers(httpHeaders).build();
        SearchMoviesResult result;
        try {
            result = restTemplate.exchange(requestEntity, SearchMoviesResult.class).getBody();
            Objects.requireNonNull(result);
        } catch (RestClientException exception){
            log.warn("exception: {}", exception.getMessage());
            throw new RuntimeException();
        }
        log.debug("movie result size: {}", result.getMovieDetails().size());
        return result;
    }
    
    private HttpHeaders makeAuthorizedHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);
        return httpHeaders;
    }
    
    private URI makeUrl(String movieName, String language) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                                   .path(path)
                                   .queryParam(MOVIE_SEARCH_PARAM_NAME, movieName)
                                   .queryParam(LANGUAGE_PARAM_NAME, language)
                                   .encode()
                                   .build()
                                   .toUri();
    }
}
