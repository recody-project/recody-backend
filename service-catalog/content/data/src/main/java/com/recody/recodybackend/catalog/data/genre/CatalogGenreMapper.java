package com.recody.recodybackend.catalog.data.genre;

import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.category.GeneralCategoryMapper;
import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.contents.Genre;
import com.recody.recodybackend.genre.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper( componentModel = "spring",
         uses = {CategoryMapper.class} )
public abstract class CatalogGenreMapper {
    
    @Mapping( target = "genreId", source = "entity.id", qualifiedByName = "genreIdMapper" )
    @Mapping( target = "category", qualifiedBy = GeneralCategoryMapper.class)
    @Mapping( target = "genreName", source = "entity.name", qualifiedByName = "genreNameMapper")
    @Mapping( target = "genreIconUrl", source = "entity.iconUrl", qualifiedByName = "genreIconUrlMapper")
    public abstract CustomGenre toCustomGenre(CatalogGenreEntity entity);
    
    @Named( "genreIdMapper" )
    public CustomGenreId mapGenreId(String genreId) {
        return CustomGenreId.of( genreId );
    }
    
    @Named( "genreNameMapper" )
    public CustomGenreName mapGenreName(String genreName) {
        return CustomGenreName.of( genreName );
    }
    @Named( "genreIconUrlMapper" )
    public CustomGenreIconUrl mapGenreIconUrl(String genreIconUrl) {
        return CustomGenreIconUrl.of( genreIconUrl );
    }
    
    
    @Named( "personalizedGenreToGenre" )
    public Genre mapGenre(PersonalizedGenreEntity entity){
        return mapGenre( entity.getGenre() );
    }
    
    @IterableMapping(qualifiedByName = "personalizedGenreToGenre")
    public abstract List<Genre> mapGenres(List<PersonalizedGenreEntity> entities);
    
    public Genre mapGenre(CatalogGenreEntity genreEntity) {
        String genreId = genreEntity.getId();
        if (genreId.startsWith( Recody.MOVIE_GENRE_PREFIX )){
            return toBasicGenre( genreEntity );
        }
        else {
            return this.toCustomGenre( genreEntity );
        }
    }
    
    @Mapping( target = "genreName", source = "entity.name" )
    @Mapping( target = "genreId", source = "entity.id" )
    public abstract BasicGenre toBasicGenre(CatalogGenreEntity entity);
    
    @Mapping( target = "user", ignore = true )
    @Mapping( target = "iconUrl", ignore = true )
    @Mapping( target = "name", source = "genre.genreName" )
    @Mapping( target = "id", source = "genre.genreId" )
    public abstract CatalogGenreEntity newEntity(Genre genre);
    
    public abstract List<CatalogGenreEntity> newEntity(List<Genre> genres);
}
