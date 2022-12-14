package com.recody.recodybackend.catalog.features.getmoviedetail;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMovieDetail {
    
    private Integer movieId;
    private String language;
    private Long userId;
    
}
