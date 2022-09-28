package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieGenreRelation;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import org.aspectj.lang.annotation.After;
import org.mapstruct.*;

@Mapper(componentModel = "spring", imports = {MovieSource.class})
public interface MovieGenreMapper {
    
    // TODO TMDB 가 아닐 수도 있다.
    @Mapping(target = "tmdbGenreId", source = "genre.genreId", conditionExpression = "java(genre.getMovieSource() == MovieSource.TMDB)")
    @Mapping(target = "tmdbGenreName", source = "genre.genreName", conditionExpression = "java(genre.getMovieSource() == MovieSource.TMDB)")
    MovieGenreEntity toEntity(MovieGenre genre);
    
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
