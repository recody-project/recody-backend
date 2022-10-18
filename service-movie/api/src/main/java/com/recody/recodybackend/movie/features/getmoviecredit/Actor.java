package com.recody.recodybackend.movie.features.getmoviecredit;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Actor {

    private Long id;
    
    private Long tmdbId;
    private String name;
    
    private String profilePath;
    
    private String character;
    
    @Override
    public String toString() {
        return "{\"Actor\":{" + "\"id\":" + id + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
