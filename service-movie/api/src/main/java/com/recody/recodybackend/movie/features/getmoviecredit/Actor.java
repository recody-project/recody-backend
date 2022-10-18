package com.recody.recodybackend.movie.features.getmoviecredit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Actor {
    @JsonIgnore // ID 세팅을 mapper 에서의 쿼리가 많아 현재는 노출하지 않도록 하였음.
    private Long id;
    
    @JsonIgnore // tmdb id 는 노출하지 않는다.
    private Long tmdbId;
    private String name;
    
    private String profilePath;
    
    private String character;
    
    @Override
    public String toString() {
        return "{\"Actor\":{" + "\"id\":" + id + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
