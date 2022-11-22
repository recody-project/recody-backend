package com.recody.recodybackend.event;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.recody.recodybackend.common.contents.ContentId;
import com.recody.recodybackend.common.contents.GenreIds;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenrePersonalized {
    
    @Builder.Default
    private CatalogEventType type = CatalogEventType.GenrePersonalized;
    
    private Long userId;
    @JsonUnwrapped
    private ContentId contentId;
    @JsonUnwrapped
    private GenreIds genreIds;

}
