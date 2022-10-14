package com.recody.recodybackend.movie.features.getmoviedetail.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionCountry {
    @NonNull
    private String iso_3166_1;
    @NonNull
    private String name;
    
    @Override
    public String toString() {
        return "{\"ProductionCountry\":{" + "\"iso_3166_1\":" + ((iso_3166_1 != null) ? ("\"" + iso_3166_1 + "\"") : null) + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
