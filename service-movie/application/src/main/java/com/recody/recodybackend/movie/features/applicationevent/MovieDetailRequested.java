package com.recody.recodybackend.movie.features.applicationevent;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieDetailRequested {
    
    // 필요에따라 tmdbId 를 사용할 것.
    private Integer tmdbId;
    
}
