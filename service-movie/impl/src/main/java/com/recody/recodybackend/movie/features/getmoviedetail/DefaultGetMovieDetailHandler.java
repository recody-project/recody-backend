package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.movie.MovieDetailViewModel;
import com.recody.recodybackend.movie.data.movie.MovieDetailMapper;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.features.applicationevent.MovieDetailRequested;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMovieDetailHandler implements GetMovieDetailHandler<MovieDetailViewModel> {
    
    private final MovieDetailMapper mapper;
    private final MovieRepository movieRepository;
    
    private final ApplicationEventPublisher eventPublisher;
    
    @Override
    public MovieDetailViewModel handle(GetMovieDetail query) {
        Locale locale = query.getLocale();
        String movieId = query.getMovieId();
        
        Optional<MovieEntity> optionalMovie = movieRepository.findById( movieId );
        if (optionalMovie.isEmpty()) {
            return null;
        }
        MovieEntity entity = optionalMovie.get();
        
        eventPublisher.publishEvent( MovieDetailRequested.builder().movieId( entity.getId() ).tmdbId( entity.getTmdbId() ).build() );
        
        // 이 movie 는 레코디의 movie. 장르정보는 고유 장르 id 를 가지고 있다.
        MovieDetailViewModel movieDetailViewModel = mapper.toViewModel( entity, locale );
        log.debug("found movieDetail from db: {}", movieDetailViewModel);
        return movieDetailViewModel;
    }
}
