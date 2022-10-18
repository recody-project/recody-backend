package com.recody.recodybackend.movie.features.getmoviecredit;

import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBGetMovieCreditResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class TMDBGetMovieCreditHandler implements GetMovieCreditHandler {
    
    private static final String TMDB_BASE_URI = "https://api.themoviedb.org/3";
    public static final String ACTING = "Acting";
    public static final String DIRECTOR = "Director";
    public static final String path = "/movie/";
    public static final String path2 = "/credits";
    private final String API_KEY_PARAM_NAME = "api_key";
    private final RestTemplate restTemplate = new RestTemplate();
    
    private final ActorMapper actorMapper;
    
    private final DirectorMapper directorMapper;
    
    public static final int ACTOR_MAX_SIZE = 5;
    
    @Value("${movie.tmdb.api-key}")
    private String apiKey;
    
    @Override
    public GetMovieCreditResult handle(GetMovieCredit command) {
        log.debug("handling command: {}", command);
        Long movieId = command.getMovieId();
        TMDBGetMovieCreditResponse response;
        URI url = makeUrl(movieId);
        RequestEntity<Void> requestEntity = RequestEntity.get(url).build();
        try {
            response = restTemplate.exchange(requestEntity, TMDBGetMovieCreditResponse.class).getBody();
            // null check
            Objects.requireNonNull(response);
        } catch (RestClientException exception4xx) {
            log.warn("exception4xx: {}", exception4xx.getMessage());
            throw new RuntimeException();
        }
        
        List<Actor> actors;
        List<Director> directors;
        List<TMDBCast> cast = response.getCast();
        List<TMDBCrew> crew = response.getCrew();
        
        actors = cast.stream()
                     .filter(tmdbCast -> tmdbCast.getKnownForDepartment().equals(ACTING))
                     .limit(ACTOR_MAX_SIZE)
                     .map(actorMapper::map)
                     .collect(Collectors.toList());
    
        directors = crew.stream()
                     .filter(tmdbCast -> tmdbCast.getJob().equals(DIRECTOR))
                     .limit(ACTOR_MAX_SIZE)
                     .map(directorMapper::map)
                     .collect(Collectors.toList());
        
        return GetMovieCreditResult.builder()
                       .actors(actors)
                       .directors(directors)
                                   .build();
    }
    
    @Override
    @Async
    public CompletableFuture<GetMovieCreditResult> handleAsync(GetMovieCredit command) {
        return CompletableFuture.completedFuture(this.handle(command));
    }
    
    private URI makeUrl(Long movieId) {
        return UriComponentsBuilder.fromUriString(TMDB_BASE_URI)
                                   .path(path + movieId + path2)
                                   .queryParam(API_KEY_PARAM_NAME, apiKey)
                                   .encode()
                                   .build()
                                   .toUri();
    }
}
