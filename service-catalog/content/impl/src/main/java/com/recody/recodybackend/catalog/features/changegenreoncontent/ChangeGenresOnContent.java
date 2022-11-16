package com.recody.recodybackend.catalog.features.changegenreoncontent;

import com.recody.recodybackend.content.ContentId;
import com.recody.recodybackend.genre.GenreIds;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeGenresOnContent {
    
    private Long userId;
    private ContentId contentId;
    private GenreIds genreIds;
    
}
