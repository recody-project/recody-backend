package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DefaultMovieService implements MovieService{
    
    private final GetMovieDetailHandler getMovieDetailHandler;
    
    
    @Override
    public GetMovieDetailResult getMovieDetail(GetMovieDetail command) {
        Movie movie = getMovieDetailHandler.handle(command);
        
        return GetMovieDetailResult.builder()
                       .detail(movie)
                       .requestInfo(command)
                                   .build();
    }
}
