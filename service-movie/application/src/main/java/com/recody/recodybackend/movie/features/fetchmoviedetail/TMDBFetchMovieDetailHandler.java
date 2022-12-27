package com.recody.recodybackend.movie.features.fetchmoviedetail;

import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb.GetMovieDetailWithTMDBId;
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
class TMDBFetchMovieDetailHandler implements FetchMovieDetailHandler<TMDBMovieDetail, GetMovieDetailWithTMDBId> {
    private static final String baseUrl = "https://api.themoviedb.org/3";
    private static final String PATH = "/movie/";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    public static final String API_KEY_PARAM_NAME = "api_key";
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${movie.tmdb.api-key}")
    private String apiKey;
    
    public TMDBMovieDetail handle(GetMovieDetailWithTMDBId args) {
        log.debug("handling args: {}", args);
        String language = args.getLanguage();
        Integer movieId = args.getTmdbId();
    
        URI url = makeUrl(movieId, language);
        RequestEntity<Void> request = RequestEntity.get(url).build();
        TMDBMovieDetail tmdbMovieDetail;
        try {
            tmdbMovieDetail = restTemplate.exchange(request, TMDBMovieDetail.class).getBody();
            // null check
            Objects.requireNonNull(tmdbMovieDetail);
            log.debug("fetched Movie Detail from TMDB");
            return tmdbMovieDetail;
        } catch (RestClientException exception4xx) {
            log.warn("exception4xx: {}", exception4xx.getMessage());
            throw new RuntimeException();
        }
    }
    
    private URI makeUrl(Integer movieId, String language) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                                   .path(PATH + movieId)
                                   .queryParam(TMDB_LANGUAGE_PARAM_NAME, language)
                                   .queryParam(API_KEY_PARAM_NAME, apiKey)
                                   .encode()
                                   .build()
                                   .toUri();
    }
    
}
