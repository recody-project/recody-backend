package com.recody.recodybackend.drama.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBDrama {

    private Integer id;
    private String name;
    
    private String overview;
    
    @JsonAlias("genre_ids")
    private List<Integer> genreIds;
    
    @JsonAlias("original_name")
    private String originalName;
    
    @JsonAlias("poster_path")
    private String posterPath;
    
    @Override
    public String toString() {
        return "{\"TMDBDrama\":{"
               + "\"id\":" + id
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + ", \"genreIds\":" + genreIds
               + ", \"originalName\":" + ((originalName != null) ? ("\"" + originalName + "\"") : null)
               + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null)
               + "}}";
    }
}
