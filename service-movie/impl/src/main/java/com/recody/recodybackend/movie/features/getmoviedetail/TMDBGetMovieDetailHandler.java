package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.common.openapi.APIRequester;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.movie.MovieEntityMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.features.recognize.MovieManager;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.general.MovieSource;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
class TMDBGetMovieDetailHandler implements GetMovieDetailHandler {
    
    private final APIRequester<TMDBAPIRequest> requester;
    private final MovieEntityMapper mapper;
    private final MovieManager movieManager;
    private final MovieRepository movieRepository;
    
    @Override
    public Movie handle(GetMovieDetail command) {
        TMDBGetMovieDetailRequest request = new TMDBGetMovieDetailRequest(command.getMovieId(), command.getLanguage());
        TMDBMovieDetail tmdbMovieDetail = requester.requestAndGet(request, TMDBMovieDetail.class);
        Movie movie = mapper.map(tmdbMovieDetail);
        movie.setPosterPath(TMDB.fullPosterPath(tmdbMovieDetail.getPosterPath()));
    
        // movie detail 저장
        String movieId = movieManager.register(movie);
        movie.setMovieId(movieId);
        log.info("movieId: {}", movieId);
        return movie;
    }
    
    @Override
    @Transactional
    public GetMovieDetailResult handleFromDB(GetMovieDetail command) {
        Optional<MovieEntity> optionalMovie = movieRepository.findByTmdbId(Integer.parseInt(command.getMovieId()));
        if (optionalMovie.isEmpty()){
            throw new RuntimeException("못찾음 " + command.getMovieId());
        }
        
        // 이 movie 는 레코디의 movie. 장르정보는 고유 장르 id 를 가지고 있다.
        Movie movie = mapper.map(optionalMovie.get(), MovieSource.TMDB);
        return GetMovieDetailResult.builder()
                                   .requestInfo(command)
                                   .detail(movie)
                                   .build();
    }
}
