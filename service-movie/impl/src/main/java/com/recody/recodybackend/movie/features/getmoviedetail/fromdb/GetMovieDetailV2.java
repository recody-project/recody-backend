package com.recody.recodybackend.movie.features.getmoviedetail.fromdb;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMovieDetailV2 {
    
    // TODO: 객체로 정의하기
    private String movieId;
    
    private Locale locale;
    
    
}
