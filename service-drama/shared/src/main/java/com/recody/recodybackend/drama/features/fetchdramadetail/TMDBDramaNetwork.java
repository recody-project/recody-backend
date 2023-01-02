package com.recody.recodybackend.drama.features.fetchdramadetail;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBDramaNetwork {
    
    private Integer id;
    private String name;
    @JsonAlias("logo_path")
    private String logoPath;
    @JsonAlias("origin_country")
    private String originCountry;
    
    @Override
    public String toString() {
        return "{\"TMDBDramaNetwork\":{"
               + "\"id\":" + id
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + ", \"logoPath\":" + ((logoPath != null) ? ("\"" + logoPath + "\"") : null)
               + ", \"originCountry\":" + ((originCountry != null) ? ("\"" + originCountry + "\"") : null)
               + "}}";
    }
}
