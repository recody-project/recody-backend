package com.recody.recodybackend.drama.features.registerdramadetail;

import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaDetail;
import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterDramaDetail {
    
    private TMDBDramaDetail detail;
    
    private Locale locale;
    
}
