package com.recody.recodybackend.drama.features.fetchdramacredit;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBDramaCast {
    
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
        return "{\"TMDBDramaCast\":{"
               + "\"id\":" + id
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + ", \"knownForDepartment\":" + ((knownForDepartment != null) ? ("\"" + knownForDepartment + "\"") : null)
               + ", \"originalName\":" + ((originalName != null) ? ("\"" + originalName + "\"") : null)
               + ", \"profilePath\":" + ((profilePath != null) ? ("\"" + profilePath + "\"") : null)
               + "}}";
    }
}
