package com.recody.recodybackend.movie.data.genre;

import com.recody.recodybackend.movie.MovieGenre;
import com.recody.recodybackend.movie.MovieSource;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        imports = MovieSource.class
        )
public abstract class MovieGenreMapper {
    
    @Mapping(target = "genreId", ignore = true)
    @Mapping(target = "tmdbGenreId", source = "genre.genreId")
    @Mapping(target = "tmdbGenreName", source = "genre.genreName")
    public abstract MovieGenreCodeEntity newEntity(MovieGenre genre);
    
    @Mapping(target = "genreId", source = "entity.genreId")
    @Mapping(target = "genreName", source = "entity.tmdbGenreName")
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    public abstract MovieGenre map(MovieGenreCodeEntity entity, MovieSource source);
    
    @Mapping(target = "genreId", source = "entity.genreId")
    @Mapping(target = "genreName", source = "entity.tmdbGenreName")
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    public abstract MovieGenre map(MovieGenreCodeEntity entity);
    
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    @Mapping(target = "genreName", source = "entity.genre.tmdbGenreName")
    @Mapping(target = "genreId", source = "entity.genre.genreId")
    public abstract MovieGenre map(MovieGenreEntity entity);
    
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    @Mapping(target = "genreId", ignore = true)
    @Mapping(target = "genreName", source = "tmdbMovieGenre.name")
    public abstract MovieGenre map(TMDBMovieGenre tmdbMovieGenre);
    
    
    public abstract List<MovieGenre> map(List<TMDBMovieGenre> tmdbMovieGenreList);
    
}
