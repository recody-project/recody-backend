package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.genre.CustomGenre;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCustomGenreResponse {
    
    private CustomGenre genre;
    
}
