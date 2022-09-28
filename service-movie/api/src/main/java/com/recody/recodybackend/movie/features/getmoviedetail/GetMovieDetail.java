package com.recody.recodybackend.movie.features.getmoviedetail;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GetMovieDetail{
    
    private String movieId;
    private String language;
}
