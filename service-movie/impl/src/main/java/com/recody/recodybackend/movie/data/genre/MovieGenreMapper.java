package com.recody.recodybackend.movie.data.genre;

import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {MovieSource.class})
public interface MovieGenreMapper {
    
    // TODO TMDB 가 아닐 수도 있다.
    @Mapping(target = "tmdbGenreId", source = "genre.genreId", conditionExpression = "java(genre.getSource() == MovieSource.TMDB)")
    @Mapping(target = "tmdbGenreName", source = "genre.genreName", conditionExpression = "java(genre.getSource() == MovieSource.TMDB)")
    MovieGenreEntity toEntity(MovieGenre genre);
    
    @Mapping(target = "genreId", source = "entity.tmdbGenreId", conditionExpression = "java(MovieSource.TMDB.equals(source))")
    @Mapping(target = "genreName", source = "entity.tmdbGenreName", conditionExpression = "java(MovieSource.TMDB.equals(source))")
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    MovieGenre map(MovieGenreEntity entity, MovieSource source);
    
    @Mapping(target = "genreId", source = "entity.tmdbGenreId")
    @Mapping(target = "genreName", source = "entity.tmdbGenreName")
    @Mapping(target = "source", expression = "java(MovieSource.TMDB)")
    MovieGenre map(MovieGenreEntity entity);
    
//    class JpaContext{
//
//        private MovieGenreEntity movieEntity;
//
//        @BeforeMapping
//        void initEntity(@MappingTarget MovieGenreEntity movieEntity){
//            this.movieEntity = movieEntity;
//        }
//
//        @AfterMapping
//        void setMembers(@MappingTarget MovieGenreRelation relation){
//            relation.setGenre(movieEntity);
//        }
//    }
}
