package com.recody.recodybackend.movie.features.updateoverview;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.features.fetchmoviedetail.FetchMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultUpdateOverviewHandler implements UpdateOverviewHandler<Void>{
    
    private final FetchMovieDetailHandler<TMDBMovieDetail, GetMovieDetail> fetchMovieDetailHandler;
    
    private final MovieRepository movieRepository;
    
    @Override
    @Transactional
    public Void handle(UpdateEnglishOverview command) {
        log.debug( "handling command: {}", command );
        Integer tmdbMovieId = command.getTmdbMovieId();
        GetMovieDetail query = GetMovieDetail.builder().tmdbId( tmdbMovieId ).language( "en" ).build();
        TMDBMovieDetail fetchedMovieDetail = fetchMovieDetailHandler.handle( query );
        String fetchedOverview = fetchedMovieDetail.getOverview();
        Optional<MovieEntity> optionalMovie = movieRepository.findByTmdbId( tmdbMovieId );
        if ( optionalMovie.isEmpty() ) {
            log.error( "Overview 업데이트할 때 영화 데이터를 DB 에서 찾을 수 없었습니다.: {}", optionalMovie );
            return null;
        }
        MovieEntity movieEntity = optionalMovie.get();
        movieEntity.getOverview().setEnglishOverview( fetchedOverview );
        log.info( "영화 {} 에 대한 영어 Overview 를 업데이트하였습니다.", movieEntity.getId() );
        return null;
    }
}
