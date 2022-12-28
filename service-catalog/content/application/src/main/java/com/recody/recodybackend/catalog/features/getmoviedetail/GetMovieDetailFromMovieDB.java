package com.recody.recodybackend.catalog.features.getmoviedetail;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMovieDetailFromMovieDB {
    
    private String movieId;
    
    private Locale locale;
    
    private Long userId;
    
}
