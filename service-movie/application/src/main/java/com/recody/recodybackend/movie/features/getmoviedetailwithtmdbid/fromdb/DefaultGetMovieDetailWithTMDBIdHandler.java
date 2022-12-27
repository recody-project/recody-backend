package com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb;

import com.recody.recodybackend.movie.MovieDetailViewModel;
import com.recody.recodybackend.movie.data.movie.MovieDetailMapper;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.features.applicationevent.MovieDetailRequested;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
class DefaultGetMovieDetailWithTMDBIdHandler implements GetMovieDetailWithTMDBIdHandler<MovieDetailViewModel> {
    
    private final MovieDetailMapper mapper;
    private final MovieRepository movieRepository;
    private final ApplicationEventPublisher eventPublisher;
    
    
    @Override
    @Transactional
    public MovieDetailViewModel handle(GetMovieDetailWithTMDBId command) {
        log.debug( "handling command: {}", command );
        Integer tmdbId = command.getTmdbId();
        eventPublisher.publishEvent( MovieDetailRequested.builder().tmdbId( tmdbId ).build() );
    
        Optional<MovieEntity> optionalMovie = movieRepository.findByTmdbId( tmdbId );
        if ( optionalMovie.isEmpty() ) {
            return null;
        }
        MovieEntity entity = optionalMovie.get();
        
        // 이 movie 는 레코디의 movie. 장르정보는 고유 장르 id 를 가지고 있다.
        MovieDetailViewModel movieDetailViewModel =
                mapper.toViewModel( entity,
                                    Locale.forLanguageTag( command.getLanguage() ) );
        log.debug( "found movieDetail from db: {}", movieDetailViewModel );
        return movieDetailViewModel;
    }
}
