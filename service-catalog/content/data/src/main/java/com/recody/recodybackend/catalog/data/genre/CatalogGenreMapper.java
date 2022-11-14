package com.recody.recodybackend.catalog.data.genre;

import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.category.GeneralCategoryMapper;
import com.recody.recodybackend.genre.CustomGenre;
import com.recody.recodybackend.genre.CustomGenreIconUrl;
import com.recody.recodybackend.genre.CustomGenreId;
import com.recody.recodybackend.genre.CustomGenreName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper( componentModel = "spring",
         uses = {CategoryMapper.class} )
public abstract class CatalogGenreMapper {
    
    @Mapping( target = "genreId", source = "entity.id", qualifiedByName = "genreIdMapper" )
    @Mapping( target = "category", qualifiedBy = GeneralCategoryMapper.class)
    @Mapping( target = "genreName", source = "entity.name", qualifiedByName = "genreNameMapper")
    @Mapping( target = "genreIconUrl", source = "entity.iconUrl", qualifiedByName = "genreIconUrlMapper")
    public abstract CustomGenre map(CatalogGenreEntity entity);
    
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
}
