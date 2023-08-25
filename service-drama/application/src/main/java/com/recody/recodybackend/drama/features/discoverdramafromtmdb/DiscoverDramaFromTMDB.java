package com.recody.recodybackend.drama.features.discoverdramafromtmdb;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscoverDramaFromTMDB {

    private String language;

    private Integer page;

}
