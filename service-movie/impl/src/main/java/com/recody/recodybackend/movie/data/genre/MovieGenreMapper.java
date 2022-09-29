package com.recody.recodybackend.movie.data.genre;

import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring",
//        uses = GenreEntityResolver.class,
        imports = MovieSource.class
        )
public abstract class MovieGenreMapper {
    
    @Autowired
    private MovieGenreRepository genreRepository;
    
    // TODO TMDB 가 아닐 수도 있다.
    @Mapping(target = "genreId", source = "genre.genreId", qualifiedByName = "resolveGenreId")
    @Mapping(target = "tmdbGenreId", source = "genre.genreId")
    @Mapping(target = "tmdbGenreName", source = "genre.genreName")
    public abstract MovieGenreEntity map(MovieGenre genre);
    
    @Mapping(target = "genreId", source = "entity.tmdbGenreId", conditionExpression = "java(MovieSource.TMDB.equals(source))")
    @Mapping(target = "genreName", source = "entity.tmdbGenreName", conditionExpression = "java(MovieSource.TMDB.equals(source))")
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    public abstract MovieGenre map(MovieGenreEntity entity, MovieSource source);
    
    @Mapping(target = "genreId", source = "entity.tmdbGenreId")
    @Mapping(target = "genreName", source = "entity.tmdbGenreName")
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    public abstract MovieGenre map(MovieGenreEntity entity);
    
    @Named(value = "resolveGenreId")
    public String getGenreId(Integer tmdbId){
        Optional<MovieGenreEntity> optionalEntity = genreRepository.findByTmdbGenreId(tmdbId);
        if (optionalEntity.isEmpty()) {
            return null;
        } else {
            return optionalEntity.get().getGenreId();
        }
    
    }
}
