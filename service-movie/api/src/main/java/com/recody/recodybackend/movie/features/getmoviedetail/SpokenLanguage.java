package com.recody.recodybackend.movie.features.getmoviedetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpokenLanguage {
    private String iso_639_1;
    private String name;
}
