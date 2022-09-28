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
    
    @Override
    public String toString() {
        return "{\"SpokenLanguage\":{" + "\"iso_639_1\":" + ((iso_639_1 != null) ? ("\"" + iso_639_1 + "\"") : null) + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
