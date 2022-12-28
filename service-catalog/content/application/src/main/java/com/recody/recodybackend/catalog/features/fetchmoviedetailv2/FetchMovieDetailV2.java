package com.recody.recodybackend.catalog.features.fetchmoviedetailv2;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchMovieDetailV2 {
    
    private String movieId;
    
    private Locale locale;

}
