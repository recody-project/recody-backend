package com.recody.recodybackend.drama.features.fetchpersondetail;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchDramaPersonDetail {
    
    public Integer tmdbPersonId;
    
    @Override
    public String toString() {
        return "{\"FetchPersonName\":{"
               + "\"tmdbPersonId\":" + tmdbPersonId
               + "}}";
    }
}
