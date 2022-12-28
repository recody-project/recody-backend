package com.recody.recodybackend.catalog.features.fetchmoviedetail;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FetchMovieDetail {
    
    private Integer movieId;
    private String language;
}
