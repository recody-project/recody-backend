package com.recody.recodybackend.drama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DramaDetail {
    
    private String id;
    private String title;
    private String overview;
    private String imageUrl;
    private String firstAirDate;
    private Integer numberOfEpisodes;
    private String actors;
    private String directors;
    
    /**
     * 방송사.
     * - network(broadcast network) 는 좀더 넓은 개념.
     */
    @JsonProperty("broadcastingStation")
    private String networks;
    
    @Override
    public String toString() {
        return "{\"DramaDetail\":{"
               + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null)
               + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null)
               + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null)
               + ", \"imageUrl\":" + ((imageUrl != null) ? ("\"" + imageUrl + "\"") : null)
               + ", \"firstAirDate\":" + ((firstAirDate != null) ? ("\"" + firstAirDate + "\"") : null)
               + ", \"numberOfEpisodes\":" + numberOfEpisodes
               + ", \"actors\":" + ((actors != null) ? ("\"" + actors + "\"") : null)
               + ", \"directors\":" + ((directors != null) ? ("\"" + directors + "\"") : null)
               + ", \"networks\":" + ((networks != null) ? ("\"" + networks + "\"") : null)
               + "}}";
    }
}
