package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieEntityMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
class TMDBGetMovieDetailHandler implements GetMovieDetailHandler {
    
    private static final String baseUrl = "https://api.themoviedb.org/3";
    private static final String PATH = "/movie/";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    public static final String API_KEY_PARAM_NAME = "api_key";
    private final MovieEntityMapper mapper;
    private final MovieManager movieManager;
    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${movie.tmdb.api-key}")
    private String apiKey;
    
    @Override
    public Movie handle(GetMovieDetail command) {
        log.debug("handling command: {}", command);
        String language = command.getLanguage();
        String movieId = command.getMovieId();
        
        URI url = makeUrl(movieId, language);
        RequestEntity<Void> RE = RequestEntity.get(url).build();
        TMDBMovieDetail tmdbMovieDetail;
        try {
            tmdbMovieDetail = restTemplate.exchange(RE, TMDBMovieDetail.class).getBody();
            // null check
            Objects.requireNonNull(tmdbMovieDetail);
        } catch (RestClientException exception4xx) {
            log.warn("exception4xx: {}", exception4xx.getMessage());
            throw new RuntimeException();
        }
        
        log.debug("tmdbMovieDetail: {}", tmdbMovieDetail);
        Movie movie = mapper.map(tmdbMovieDetail);
        movie.setPosterPath(TMDB.fullPosterPath(tmdbMovieDetail.getPosterPath()));
        
        // movie detail 저장
        // 요청한 language 에 따라서 다른 title 을 저장해야한다.
        String savedMovieId = movieManager.register(movie, Locale.forLanguageTag(language));
        movie.setMovieId(savedMovieId);
        log.info("movieId: {}", savedMovieId);
        return movie;
    }
    
    @Override
    @Transactional
    public GetMovieDetailResult handleFromDB(GetMovieDetail command) {
        Optional<MovieEntity> optionalMovie = movieRepository.findByTmdbId(Integer.parseInt(command.getMovieId()));
        if (optionalMovie.isEmpty()) {
            throw new RuntimeException("못찾음 " + command.getMovieId());
        }
        
        // 이 movie 는 레코디의 movie. 장르정보는 고유 장르 id 를 가지고 있다.
        Movie movie = mapper.map(optionalMovie.get(), MovieSource.TMDB, Locale.forLanguageTag(command.getLanguage()));
        return GetMovieDetailResult.builder().requestInfo(command).detail(movie).build();
    }
    
    private URI makeUrl(String movieId, String language) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                                   .path(PATH + movieId)
                                   .queryParam(TMDB_LANGUAGE_PARAM_NAME, language)
                                   .queryParam(API_KEY_PARAM_NAME, apiKey)
                                   .encode()
                                   .build()
                                   .toUri();
    }
}
