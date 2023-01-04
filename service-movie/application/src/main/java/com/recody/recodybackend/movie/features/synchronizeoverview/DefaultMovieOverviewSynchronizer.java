package com.recody.recodybackend.movie.features.synchronizeoverview;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.overview.MovieOverviewEntity;
import com.recody.recodybackend.movie.features.fetchmoviedetail.FetchMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb.GetMovieDetailWithTMDBId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieOverviewSynchronizer implements MovieOverviewSynchronizer {
    
    private final MovieRepository movieRepository;
    private final FetchMovieDetailHandler<TMDBMovieDetail, GetMovieDetailWithTMDBId> movieDetailFromTMDBHandler;
    
    @Override
    @Async(value = Recody.MOVIE_TASK_EXECUTOR )
    @Transactional
    public void synchronize() {
        try {
            Thread.sleep( 5000L );
        } catch ( InterruptedException e ) {
            throw new RuntimeException( e );
        }
        
        // 여기부터 트랜잭션으로 묶어야함.
        Page<MovieEntity> allMoviesPage = movieRepository.findAll( PageRequest.of(12, 10) );
        List<MovieEntity> allMovies = allMoviesPage.getContent();
        log.info( "allMovies.size(): {}", allMovies.size() );
        
        for (MovieEntity movie : allMovies) {
            log.info( "영화 {} 에 대한 Overview 업데이트 시도", movie.getId() );
            
            try {
                Thread.sleep( 100L );
            } catch ( InterruptedException e ) {
                throw new RuntimeException( e );
            }
    
            MovieOverviewEntity overview = movie.getOverview();
            String koreanOverview;
            if (overview == null){
                MovieOverviewEntity movieOverviewEntity = new MovieOverviewEntity();
                String fetchedOverview = fetchDetailAndReturnOverview( movie );
                movieOverviewEntity.setKoreanOverview( fetchedOverview );
                movie.setOverview( movieOverviewEntity );
                log.info( "업데이트 성공: fetchedOverview: {}", fetchedOverview );
            }
            else {
                koreanOverview = overview.getKoreanOverview();
                if (koreanOverview == null){
                    String fetchedOverview = fetchDetailAndReturnOverview( movie );
                    overview.setKoreanOverview( fetchedOverview );
                    movie.setOverview( overview );
                    log.info( "업데이트 성공: fetchedOverview: {}", fetchedOverview );
                }
                else {
                    log.info( "이미 줄거리가 있음.koreanOverview: {}", koreanOverview );
                }
            }
        }
    }
    
    private String fetchDetailAndReturnOverview(MovieEntity movie) {
        TMDBMovieDetail koreanMovieDetail = movieDetailFromTMDBHandler.handle(
                GetMovieDetailWithTMDBId.builder().tmdbId( movie.getTmdbId() )
                                        .language( "ko" )
                                        .build() );
        String fetchedOverview = koreanMovieDetail.getOverview();
        return fetchedOverview;
    }
}
