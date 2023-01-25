package com.recody.recodybackend.drama.data.genre;

import com.recody.recodybackend.drama.tmdb.genre.TMDBDramaGenre;
import org.mapstruct.*;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring")
public abstract class DramaGenreMapper {
    

    @Named( value = "genreEntityMapper")
    public DramaGenreCodeEntity newEntity(TMDBDramaGenre dramaGenre, @Context Locale locale){
        String givenLanguage = locale.getLanguage();
        DramaGenreCodeEntity.DramaGenreCodeEntityBuilder builder = DramaGenreCodeEntity.builder();
        if ( givenLanguage.equals( Locale.KOREAN.getLanguage() ) ) {
            builder.tmdbGenreId( dramaGenre.getId() );
            builder.tmdbKoreanName( dramaGenre.getName() );
            return builder.build();
        }
        else {
            builder.tmdbGenreId( dramaGenre.getId() );
            builder.tmdbEnglishName( dramaGenre.getName() );
            return builder.build();
        }
    }
    
    @IterableMapping(qualifiedByName = "genreEntityMapper")
    public abstract List<DramaGenreCodeEntity> newEntity(List<TMDBDramaGenre> dramaGenres, @Context Locale locale);
    
    
    /**
     * Locale 에 따라 entity 의 장르 이름을 업데이트 한다.
     * - id 가 같지 않을 경우 아무 것도 수행하지 않는다.
     */
    public void updateName(@MappingTarget DramaGenreCodeEntity entity, TMDBDramaGenre dramaGenre,
                           @Context Locale locale) {
        if (!entity.getTmdbGenreId().equals( dramaGenre.getId() )) return;
        
        if (locale.getLanguage().equals( Locale.KOREAN.getLanguage() )){
            entity.setTmdbKoreanName( dramaGenre.getName() );
        }
        else{
            entity.setTmdbEnglishName( dramaGenre.getName() );
        }
    }
}
