package com.recody.recodybackend.catalog.features.getdetail.movie;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FetchMovieDetail {
    
    private Integer movieId;
    private String language;
}
