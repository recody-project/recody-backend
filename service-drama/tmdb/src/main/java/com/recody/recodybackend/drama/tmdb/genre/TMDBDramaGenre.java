package com.recody.recodybackend.drama.tmdb.genre;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBDramaGenre {
    
    private Integer id;
    private String name;
    
    @Override
    public String toString() {
        return "{\"TMDBDramaGenre\":{"
               + "\"id\":" + id
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + "}}";
    }
}
