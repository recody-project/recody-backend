package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.MovieDetailViewModel;
import com.recody.recodybackend.movie.data.movie.MovieDetailMapper;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.FetchedMovieDetailViewModel;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.GetMovieDetailFromTMDBHandler;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.TMDBFetchedMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetailHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author motive
 */
@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieService implements MovieDetailService<FetchedMovieDetailViewModel, GetMovieDetail> {
    private final GetMovieDetailHandler getMovieDetailHandler;
    private final MovieDetailMapper movieDetailMapper;
    private final GetMovieDetailFromTMDBHandler getMovieDetailFromTMDBHandler;
    
    /**
     * TMDB 에서 영화의 상세정보를 가져와 반환한다.
     * 받아온 결과를 매핑하여 그대로 반환하며, 저장은 비동기로 이루어진다.
     */
    @Deprecated
    @Override
    public FetchedMovieDetailViewModel fetchMovieDetail(GetMovieDetail args) {
        TMDBFetchedMovieDetail tmdbFetchedMovieDetail = getMovieDetailFromTMDBHandler.handle( args );
        return movieDetailMapper.toViewModel( tmdbFetchedMovieDetail );
        
    }
    
    
    /**
     * DB 에서 영화정보를 가져온다.
     * 상세 정보는 MovieDetail 객체를 포함한다.
     * 주의: @Transactional 을 붙이지 말것.
     */
    @Deprecated
    @Override
    public MovieDetailViewModel getMovieDetail(GetMovieDetail command) {
        MovieDetail movieDetail = getMovieDetailHandler.handleFromDB( command );
        return movieDetailMapper.toViewModel( movieDetail );
    }
}
