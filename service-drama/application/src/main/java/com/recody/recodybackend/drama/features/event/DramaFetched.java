package com.recody.recodybackend.drama.features.event;

import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import lombok.*;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DramaFetched {
    
    private List<TMDBDrama> dramas;
    
    private Locale locale;
    
    @Override
    public String toString() {
        return "{\"DramaFetched\":{"
               + "\"dramas size\":" + dramas.size()
               + ", \"locale\":" + locale
               + "}}";
    }
}
