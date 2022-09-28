package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * TMDB Movie Detail 을 Movie 객체로 변환한다. */
@Mapper(componentModel = "spring", imports = {MovieSource.class})
interface MovieDetailMapper {
    @Mapping(target = "rootId", ignore = true)
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    Movie map(TMDBMovieDetail tmdbMovieDetail);
    
    default MovieGenre toGenre(TMDBMovieGenre tmdbMovieGenre){
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setGenreId(tmdbMovieGenre.getId());
        movieGenre.setGenreName(tmdbMovieGenre.getName());
        movieGenre.setSource(MovieSource.TMDB);
        return movieGenre;
    }
}