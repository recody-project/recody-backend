package com.recody.recodybackend.movie.features.initializemovies;

import com.recody.recodybackend.movie.features.discovermoviefromtmdb.DiscoverMovieFromTMDB;
import com.recody.recodybackend.movie.features.discovermoviefromtmdb.DiscoverMovieFromTMDBHandler;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultInitializeMoviesHandler implements InitializeMoviesHandler<Void>{

    private final DiscoverMovieFromTMDBHandler<TMDBMovieSearchResponse> discoverMovieFromTMDBHandler;
    private final MovieManager movieManager;

    private Integer totalPages;

    @Override
    public Void handle() {
        log.debug( "initialize movies");
        TMDBMovieSearchResponse movieResponse = discoverMovieFromTMDBHandler.handle(
                DiscoverMovieFromTMDB.builder()
                        .language(Locale.KOREAN.getLanguage())
                        .page(1)
                        .build());
        totalPages = movieResponse.getTotalPages();
        for (Integer page = 1; page <= totalPages; page++) {
            log.debug( "initial moveis for page: {}, total page : {}", page, totalPages );
            TMDBMovieSearchResponse response = discoverMovieFromTMDBHandler.handle(
                    DiscoverMovieFromTMDB.builder()
                            .language(Locale.KOREAN.getLanguage())
                            .page(page)
                            .build());
            movieManager.movie().register(response.getResults(), Locale.KOREAN);

        }

        return null;
    }
}
