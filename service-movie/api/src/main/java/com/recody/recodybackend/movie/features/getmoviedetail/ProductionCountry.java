package com.recody.recodybackend.movie.features.getmoviedetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionCountry {
    private String iso_3166_1;
    private String name;
    
    @Override
    public String toString() {
        return "{\"ProductionCountry\":{" + "\"iso_3166_1\":" + ((iso_3166_1 != null) ? ("\"" + iso_3166_1 + "\"") : null) + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
