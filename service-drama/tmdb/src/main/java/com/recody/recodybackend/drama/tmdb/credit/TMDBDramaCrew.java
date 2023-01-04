package com.recody.recodybackend.drama.tmdb.credit;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBDramaCrew {
    private Integer id;
    private String name;
    @JsonAlias("known_for_department")
    private String knownForDepartment;
    
    @JsonAlias("original_name")
    private String originalName;
    
    @JsonAlias("profile_path")
    private String profilePath;
    
    @Override
    public String toString() {
        return "{\"TMDBDramaCrew\":{"
               + "\"id\":" + id
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + ", \"knownForDepartment\":" + ((knownForDepartment != null) ? ("\"" + knownForDepartment + "\"") : null)
               + "}}";
    }
}
