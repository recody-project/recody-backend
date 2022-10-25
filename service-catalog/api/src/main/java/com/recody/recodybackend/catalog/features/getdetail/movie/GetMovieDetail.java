package com.recody.recodybackend.catalog.features.getdetail.movie;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMovieDetail {
    
    private Integer tmdbId;
    private String language;
    private Long userId;
    
}
