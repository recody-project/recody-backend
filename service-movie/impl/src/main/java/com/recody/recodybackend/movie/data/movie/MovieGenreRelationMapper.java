package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.movie.data.MovieGenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieGenreRelationMapper {
    
//    MovieGenreRelation map();
    
    class JpaContext{
        
        private MovieGenreRelation relation;
        
        void initEntity(@MappingTarget MovieGenreRelation relation){
            this.relation = relation;
        }
        
        void setMembers(@MappingTarget MovieEntity movie, @MappingTarget MovieGenreEntity movieGenreEntity){
            relation.setMovie(movie);
            relation.setGenre(movieGenreEntity);
        }
    }
}
