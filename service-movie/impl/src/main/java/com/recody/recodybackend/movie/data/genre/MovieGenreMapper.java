package com.recody.recodybackend.movie.data.genre;

import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        imports = MovieSource.class
        )
public abstract class MovieGenreMapper {
    
    // TODO TMDB 가 아닐 수도 있다.
    @Mapping(target = "genreId", ignore = true)
    @Mapping(target = "tmdbGenreId", source = "genre.genreId")
    @Mapping(target = "tmdbGenreName", source = "genre.genreName")
    public abstract MovieGenreCodeEntity map(MovieGenre genre);
    
    
    
    @Mapping(target = "genreId", source = "entity.tmdbGenreId", conditionExpression = "java(MovieSource.TMDB.equals(source))")
    @Mapping(target = "genreName", source = "entity.tmdbGenreName", conditionExpression = "java(MovieSource.TMDB.equals(source))")
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    public abstract MovieGenre map(MovieGenreCodeEntity entity, MovieSource source);
    
    @Mapping(target = "genreId", source = "entity.tmdbGenreId")
    @Mapping(target = "genreName", source = "entity.tmdbGenreName")
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    public abstract MovieGenre map(MovieGenreCodeEntity entity);
}
