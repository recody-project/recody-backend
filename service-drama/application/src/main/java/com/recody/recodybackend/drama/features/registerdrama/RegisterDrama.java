package com.recody.recodybackend.drama.features.registerdrama;

import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterDrama {
    
    private TMDBDrama drama;
    
    private Locale locale;
    
    @Override
    public String toString() {
        return "{\"RegisterDrama\":{"
               + "\"drama\":" + drama
               + ", \"locale\":" + locale
               + "}}";
    }
}
