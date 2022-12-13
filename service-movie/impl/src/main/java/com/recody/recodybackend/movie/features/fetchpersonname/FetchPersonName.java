package com.recody.recodybackend.movie.features.fetchpersonname;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchPersonName {
    
    public Integer tmdbPersonId;
    
    @Override
    public String toString() {
        return "{\"FetchPersonName\":{"
               + "\"tmdbPersonId\":" + tmdbPersonId
               + "}}";
    }
}
