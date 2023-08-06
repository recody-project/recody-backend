package com.recody.recodybackend.movie.features.discovermoviefromtmdb;

public interface DiscoverMovieFromTMDBHandler<R> {
    R handle(DiscoverMovieFromTMDB query);
}