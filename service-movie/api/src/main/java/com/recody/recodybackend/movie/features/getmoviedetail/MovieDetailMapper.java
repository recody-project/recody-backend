package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * TMDB Movie Detail 을 Movie 객체로 변환한다. */
@Mapper(componentModel = "spring", imports = {MovieSource.class, BasicCategory.class})
abstract class MovieDetailMapper {
    @Mapping(target = "directors", ignore = true)
    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "contentId", ignore = true) // 알 수 없다.
    @Mapping(target = "tmdbId", source = "tmdbMovieDetail.id")
    @Mapping(target = "category", expression = "java(com.recody.recodybackend.common.contents.BasicCategory.Movie)")
    @Mapping(target = "rootId", ignore = true)
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    public abstract Movie map(TMDBMovieDetail tmdbMovieDetail);
    
    public MovieGenre toGenre(TMDBMovieGenre tmdbMovieGenre){
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setGenreId(tmdbMovieGenre.getId());
        movieGenre.setGenreName(tmdbMovieGenre.getName());
        movieGenre.setSource(MovieSource.TMDB);
        return movieGenre;
    }
}
