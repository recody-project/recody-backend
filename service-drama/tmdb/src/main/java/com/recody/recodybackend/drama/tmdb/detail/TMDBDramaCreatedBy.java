package com.recody.recodybackend.drama.tmdb.detail;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBDramaCreatedBy {

    private Integer id;
    @JsonAlias("credit_id")
    private String creditId;
    private String name;
    private Integer gender;
    @JsonAlias("profile_path")
    private String profilePath;
    
    @Override
    public String toString() {
        return "{\"TMDBDramaCreatedBy\":{"
               + "\"id\":" + id
               + ", \"creditId\":" + ((creditId != null) ? ("\"" + creditId + "\"") : null)
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + ", \"gender\":" + gender
               + ", \"profilePath\":" + ((profilePath != null) ? ("\"" + profilePath + "\"") : null)
               + "}}";
    }
}
