package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
class TMDBSearchMoviesHandler implements SearchMoviesHandler<TMDBMovieSearchNode> {
    
    private static final String baseUrl = "https://api.themoviedb.org/3";
    private static final String TMDB_MOVIE_SEARCH_PATH = "/search/movie";
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    public static final String PAGE_PARAM_NAME = "page";
    public static final String API_KEY_PARAM_NAME = "api_key";
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${movie.tmdb.api-key}")
    private String apiKey;
    
    @Override
    public List<TMDBMovieSearchNode> handle(@Valid SearchMovies command) {
        log.debug("handling command: {}", command);
        String movieName = command.getMovieName();
        String language = command.getLanguage();
        Integer page = command.getPage();
    
        URI url = makeUrl(movieName, language, page);
        RequestEntity<Void> RE = RequestEntity.get(url).build();
        TMDBMovieSearchResponse response;
        try {
            response = restTemplate.exchange(RE, TMDBMovieSearchResponse.class).getBody();
            // null check
            Objects.requireNonNull(response);
        } catch (RestClientException exception4xx) {
            log.warn("exception4xx: {}", exception4xx.getMessage());
            throw new RuntimeException();
        }
        List<TMDBMovieSearchNode> tmdbMovies = response.getResults();
        log.debug("TMDB 에서 영화 검색 결과를 가져왔습니다. size: {}", tmdbMovies.size());
        return tmdbMovies;
    }
    
    private URI makeUrl(String movieName, String language, Integer page) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                                   .path(TMDB_MOVIE_SEARCH_PATH)
                                   .queryParam(TMDB_QUERY_PARAM_NAME, movieName)
                                   .queryParam(TMDB_LANGUAGE_PARAM_NAME, language)
                                   .queryParam(API_KEY_PARAM_NAME, apiKey)
                                   .queryParam( PAGE_PARAM_NAME, page )
                                   .encode()
                                   .build()
                                   .toUri();
    }
}
