package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.movie.MovieDetailViewModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMovieDetailResponse {
    
    private MovieDetailViewModel movie;
    
}
