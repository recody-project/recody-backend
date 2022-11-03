package com.recody.recodybackend.movie.features.getmoviedetail.fromapi;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBFetchMovieDetail {
    
    private Integer id;
    private String language;
    
}
