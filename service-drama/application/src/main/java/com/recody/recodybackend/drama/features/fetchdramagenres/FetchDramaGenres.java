package com.recody.recodybackend.drama.features.fetchdramagenres;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchDramaGenres {

    @Builder.Default
    private Locale locale = Locale.KOREAN;

}
