package com.recody.recodybackend.movie.features.getmoviedetail;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMovieDetail {
    
    // TODO: 객체로 정의하기
    private String movieId;
    
    private Locale locale;
    
    @Override
    public String toString() {
        return "{\"GetMovieDetail\":{"
               + "\"movieId\":" + ((movieId != null) ? ("\"" + movieId + "\"") : null)
               + ", \"locale\":" + locale
               + "}}";
    }
}
