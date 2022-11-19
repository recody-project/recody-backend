package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.event.GenrePersonalized;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeGenresOnContentResponse {
    
    private GenrePersonalized event;
    
}
