package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.MovieDetail;
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

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
class TMDBSearchMoviesHandler implements SearchMoviesHandler {
    
    private static final String baseUrl = "https://api.themoviedb.org/3";
    private static final String TMDB_MOVIE_SEARCH_PATH = "/search/movie";
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    public static final String API_KEY_PARAM_NAME = "api_key";
    private final TMDBMovieSearchMapper movieSearchMapper;
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${movie.tmdb.api-key}")
    private String apiKey;
    
    @Override
    public SearchMoviesResult handle(SearchMovies command) {
        log.debug("handling command: {}", command);
        String movieName = command.getMovieName();
        String language = command.getLanguage();
    
        URI url = makeUrl(movieName, language);
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
        
        Locale locale = resolveLocale(command);
        List<TMDBMovieSearchNode> tmdbMovies = response.getResults();
        List<MovieDetail> movieDetails = movieSearchMapper.mapList(tmdbMovies);
        
        return SearchMoviesResult.builder().movieDetails(movieDetails).requestedLanguage(locale).build();
    }
    
    private URI makeUrl(String movieName, String language) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                                   .path(TMDB_MOVIE_SEARCH_PATH)
                                   .queryParam(TMDB_QUERY_PARAM_NAME, movieName)
                                   .queryParam(TMDB_LANGUAGE_PARAM_NAME, language)
                                   .queryParam(API_KEY_PARAM_NAME, apiKey)
                                   .encode()
                                   .build()
                                   .toUri();
    }
    
    private Locale resolveLocale(SearchMovies command) {
        Locale locale;
        try {
            locale = new Locale(command.getLanguage());
        } catch (Exception exception) {
            throw new RuntimeException("language 파라미터가 올바르지 않습니다.");
        }
        return locale;
    }
}
