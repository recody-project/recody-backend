package com.recody.recodybackend.movie.data.genre;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.data.AsyncEntityRegistrar;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieGenre;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MovieGenreCodeManager extends AsyncEntityRegistrar<MovieGenreCodeEntity, TMDBMovieGenre> {
    
    /**
     * 장르가 없으면 저장하고 결과를 반환한다.
     * 저장된 entity 는 영화에 해당하는 장르와 매핑되는 MovieGenreEntity 를 저장하는 데에 사용할 수 있다.
     * @param tmdbMovieGenre TMDB 영화 장르 정보
     * @return 저장한 장르의 entity
     */
    MovieGenreCodeEntity register(TMDBMovieGenre tmdbMovieGenre);
    
    @Override
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    @Transactional
    default CompletableFuture<MovieGenreCodeEntity> registerAsync(TMDBMovieGenre tmdbMovieGenre) {
        return AsyncEntityRegistrar.super.registerAsync( tmdbMovieGenre );
    }
    @Override
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    @Transactional
    default CompletableFuture<List<MovieGenreCodeEntity>> registerAsync(List<TMDBMovieGenre> tmdbMovieGenres) {
        return AsyncEntityRegistrar.super.registerAsync( tmdbMovieGenres );
    }
}
