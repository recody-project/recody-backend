package com.recody.recodybackend.movie.features.discovermoviefromtmdb;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscoverMovieFromTMDB {
    private String language;

    private Integer page;
}
